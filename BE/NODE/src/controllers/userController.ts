import { Request, Response } from 'express';
import UserModel from '../models/user';
import bcrypt from 'bcryptjs';
import jwt from 'jsonwebtoken';

export const register = async (req: Request, res: Response) => {
  const { UserName, PassWord, Email } = req.body

  try {
    const existingUser = await UserModel.findByEmail(Email)
    if (existingUser) {
      return res.status(400).json({ message: 'User already exists' })
    }

    const hashedPassword = await bcrypt.hash(PassWord, 10)
    const newUser = {
      UserId: 'U' + Date.now().toString(),
      UserName,
      PassWord: hashedPassword,
      Avatar: '',
      Name: '',
      DateOfBirth: undefined,
      Gender: undefined,
      Phone: '',
      Email,
      AddressId: '',
      IsBan: 'unbanned' as 'unbanned',
      Verify: 'unverified' as 'unverified',
      DeletedAt: undefined
    }

    await UserModel.create(newUser)
    res.status(201).json({ message: 'User registered successfully' })
  } catch (err) {
    if (err instanceof Error) {
      res.status(500).json({ message: err.message })
    } else {
      res.status(500).json({ message: 'An unknown error occurred' })
    }
  }
}

export const login = async (req: Request, res: Response) => {
  const { Email, PassWord } = req.body

  try {
    const user = await UserModel.findByEmail(Email)
    if (!user) {
      return res.status(400).json({ message: 'Invalid credentials' })
    }

    const isMatch = await bcrypt.compare(PassWord, user.PassWord)
    if (!isMatch) {
      return res.status(400).json({ message: 'Invalid credentials' })
    }

    // Ensure JWT_SECRET is a string
    const jwtSecret = process.env.JWT_SECRET
    if (!jwtSecret) {
      throw new Error('JWT_SECRET is not defined')
    }

    const token = jwt.sign({ userId: user.UserId }, jwtSecret, { expiresIn: '1h' })
    res.json({ token })
  } catch (err) {
    if (err instanceof Error) {
      res.status(500).json({ message: err.message })
    } else {
      res.status(500).json({ message: 'An unknown error occurred' })
    }
  }
}

export const forgotPassword = async (req: Request, res: Response) => {
  const { Email } = req.body

  try {
    const user = await UserModel.findByEmail(Email)
    if (!user) {
      return res.status(400).json({ message: 'User not found' })
    }

    // Ensure JWT_SECRET is a string
    const jwtSecret = process.env.JWT_SECRET
    if (!jwtSecret) {
      throw new Error('JWT_SECRET is not defined')
    }

    const token = jwt.sign({ userId: user.UserId }, jwtSecret, { expiresIn: '15m' })
    res.json({ message: 'Password reset link has been sent to your email', token })
  } catch (err) {
    if (err instanceof Error) {
      res.status(500).json({ message: err.message })
    } else {
      res.status(500).json({ message: 'An unknown error occurred' })
    }
  }
}