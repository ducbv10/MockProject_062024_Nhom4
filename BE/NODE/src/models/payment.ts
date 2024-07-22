import { DateTime, VarChar } from 'mssql';
import { poolPromise } from '../config/db';

interface Payment {
  PaymentId?: string;
  UserId?: string;
  BankName: string;
  BankNum: string;
  BankBranch: string;
  AccountBalance?: number;
}

async function getLastPaymentId(): Promise<string> {
  const pool = await poolPromise();
  try {
    const request = pool.request();
    const query = `
      SELECT TOP 1 PaymentId
      FROM [Payment]
      ORDER BY PaymentId DESC;
    `;
    const result = await request.query(query);
    if (result.recordset.length > 0) {
      return result.recordset[0].PaymentId;
    } else {
      return 'PM00000000'; // Return a default starting value if no Payments are found
    }
  } catch (err) {
    console.error('Error in getLastPaymentId:', err);
    throw err;
  }
}

class PaymentModel {
  static async create(payment: Payment) {
    const pool = await poolPromise();
    const lastPaymentId = await getLastPaymentId();
    let nextPaymentId = 'PM00000000';

    if (lastPaymentId) {
      const lastNum = parseInt(lastPaymentId.slice(2), 10);
      nextPaymentId = `PM${(lastNum + 1).toString().padStart(8, '0')}`;
    }

    try {
      const request = pool.request();
      request.input('PaymentId', nextPaymentId);
      request.input('UserId', payment.UserId);
      request.input('BankName', payment.BankName);
      request.input('BankNum', payment.BankNum);
      request.input('BankBranch', payment.BankBranch);

      const query = `
        INSERT INTO Payment (PaymentId, UserId, BankName, BankNum, BankBranch)
        VALUES (@PaymentId, @UserId, @BankName, @BankNum, @BankBranch)
      `;

      await request.query(query);
    } catch (err) {
      console.error('Error in PaymentModel.create:', err);
      throw err;
    }
  }

  static async findAll() {
    const pool = await poolPromise();
    try {
      const request = pool.request();

      const query = 'SELECT PaymentId, UserId, BankName, BankNum, BankBranch, AccountBalance FROM Payment';
      const result = await request.query(query);
      return result.recordset;
    } catch (err) {
      console.error('Error in PaymentModel.findAll:', err);
      throw err;
    }
  }

  static async findById(paymentId: string) {
    const pool = await poolPromise();
    try {
      const request = pool.request();
      request.input('PaymentId', paymentId);

      const query = 'SELECT PaymentId, UserId, BankName, BankNum, BankBranch, AccountBalance FROM Payment WHERE PaymentId = @PaymentId';
      const result = await request.query(query);
      return result.recordset[0];
    } catch (err) {
      console.error('Error in PaymentModel.findById:', err);
      throw err;
    }
  }

  static async update(paymentId: string, updatedFields: Partial<Payment>) {
    const pool = await poolPromise();
    try {
      const request = pool.request();
      const updateFields = Object.keys(updatedFields).map(key => `${key} = @${key}`).join(', ');

      Object.entries(updatedFields).forEach(([key, value]) => {
        request.input(key, value);
      });
      request.input('PaymentId', VarChar, paymentId);

      const query = `
            UPDATE Payment
            SET ${updateFields}
            WHERE PaymentId = @PaymentId;
        `;
      const result = await request.query(query);
      return result.rowsAffected[0] > 0 ? await PaymentModel.findById(paymentId) : null;
    } catch (err) {
      console.error('Error in PaymentModel.update:', err);
      throw err;
    }
  }

  static async delete(paymentId: string) {
    const pool = await poolPromise();
    try {
      const request = pool.request();
      request.input('PaymentId', paymentId);
      request.input('DeletedAt', DateTime, new Date());

      const query = `
        UPDATE Address
        SET DeletedAt = @DeletedAt
        WHERE PaymentId = @PaymentId;
      `;
      await request.query(query);
    } catch (err) {
      console.error('Error in PaymentModel.delete:', err);
      throw err;
    }
  }
}

export default PaymentModel;