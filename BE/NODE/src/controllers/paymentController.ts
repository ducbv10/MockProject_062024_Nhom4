import { Request, Response } from 'express';
import PaymentModel from '../models/payment';

class PaymentController {
  static async createPayment(paymentData: { UserId: string, BankName: string, BankNum: string, BankBranch: string}) {
    const { UserId, BankName, BankNum, BankBranch } = paymentData;

    try {
      const newPayment = {
        UserId,
        BankName,
        BankNum,
        BankBranch
      };

      await PaymentModel.create(newPayment);
      return { message: 'Payment created successfully' };
    } catch (err) {
      console.error('Error in PaymentController.createPayment:', err);
      throw new Error('Failed to create payment');
    }
  }

  static async findAllPayments(req: Request, res: Response) {
    try {
      const payments = await PaymentModel.findAll();
      res.json(payments);
    } catch (err) {
      console.error('Error in PaymentController.findAllPayments:', err);
      res.status(500).json({ message: 'Failed to fetch payments' });
    }
  }

  static async findPaymentById(req: Request, res: Response) {
    const { paymentId } = req.params;

    try {
      const payment = await PaymentModel.findById(paymentId);
      if (!payment) {
        return res.status(404).json({ message: 'Payment not found' });
      }
      res.json(payment);
    } catch (err) {
      console.error('Error in PaymentController.findPaymentById:', err);
      res.status(500).json({ message: 'Failed to fetch payment' });
    }
  }

  static async updatePayment(req: Request, res: Response) {
    const { paymentId } = req.params;
    const { UserId, BankName, BankNum, BankBranch, AccountBalance } = req.body;

    try {
      await PaymentModel.update(paymentId, { UserId, BankName, BankNum, BankBranch, AccountBalance });
      res.json({ message: 'Payment updated successfully' });
    } catch (err) {
      console.error('Error in PaymentController.updatePayment:', err);
      res.status(500).json({ message: 'Failed to update payment' });
    }
  }

  static async deletePayment(req: Request, res: Response) {
    const { paymentId } = req.params;

    try {
      await PaymentModel.delete(paymentId);
      res.json({ message: 'Payment deleted successfully' });
    } catch (err) {
      console.error('Error in PaymentController.deletePayment:', err);
      res.status(500).json({ message: 'Failed to delete payment' });
    }
  }
}

export default PaymentController;