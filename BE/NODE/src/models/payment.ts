import { poolPromise } from '../config/db';

interface Payment {
  PaymentId?: string;
  UserId: string;
  BankName: string;
  BankNum: string;
  BankBranch: string;
  AccountBalance?: number;
}

class PaymentModel {
  static async create(payment: Payment) {
    const pool = await poolPromise();
    try {
      const request = pool.request();
      request.input('UserId', payment.UserId);
      request.input('BankName', payment.BankName);
      request.input('BankNum', payment.BankNum);
      request.input('BankBranch', payment.BankBranch);

      const query = `
        INSERT INTO Payment (UserId, BankName, BankNum, BankBranch)
        VALUES (@UserId, @BankName, @BankNum, @BankBranch)
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
      request.input('PaymentId', paymentId);
      request.input('UserId', updatedFields.UserId);
      request.input('BankName', updatedFields.BankName);
      request.input('BankNum', updatedFields.BankNum);
      request.input('BankBranch', updatedFields.BankBranch);
      request.input('AccountBalance', updatedFields.AccountBalance);

      const query = `
        UPDATE Payment
        SET UserId = @UserId, BankName = @BankName, BankNum = @BankNum, BankBranch = @BankBranch, AccountBalance = @AccountBalance
        WHERE PaymentId = @PaymentId
      `;

      await request.query(query);
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

      const query = 'DELETE FROM Payment WHERE PaymentId = @PaymentId';
      await request.query(query);
    } catch (err) {
      console.error('Error in PaymentModel.delete:', err);
      throw err;
    }
  }
}

export default PaymentModel;