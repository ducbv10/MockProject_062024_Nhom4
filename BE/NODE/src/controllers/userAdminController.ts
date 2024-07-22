import { Request, Response } from 'express';
import UserModel from '../models/user';
import bcrypt from 'bcryptjs';
import jwt from 'jsonwebtoken';
import AddressController from '../controllers/addressController';
import PaymentController from '../controllers/paymentController';

export const login = async (req: Request, res: Response) => {
  const { email: Email, password: PassWord } = req.body;

  try {
    const user = await UserModel.findByEmail(Email);
    if (!user) {
      return res.status(400).json({ message: 'Invalid credentials' });
    }

    const isMatch = await bcrypt.compare(PassWord, user.PassWord);
    if (!isMatch) {
      return res.status(400).json({ message: 'Invalid credentials' });
    }

    const jwtSecret = process.env.JWT_SECRET;
    if (!jwtSecret) {
      throw new Error('JWT_SECRET is not defined');
    }

    const token = jwt.sign({ userId: user.UserId }, jwtSecret, { expiresIn: '24h' });
    res.json({ token });
  } catch (err) {
    if (err instanceof Error) {
      res.status(500).json({ message: err.message });
    } else {
      res.status(500).json({ message: 'An unknown error occurred' });
    }
  }
};

export const logout = async (req: Request, res: Response) => {
  try {
    // Assume the token has been deleted on the client side to mark the user as logged out

    res.json({ message: 'Logout successful' });
  } catch (err) {
    console.error('Logout Error:', err);
    res.status(500).json({ message: 'Internal Server Error' });
  }
};