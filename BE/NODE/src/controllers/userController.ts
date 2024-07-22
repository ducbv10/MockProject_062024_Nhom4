import { Request, Response } from 'express';
import UserModel from '../models/user';
import UserRoleModel from '../models/userRole';
import bcrypt from 'bcryptjs';
import jwt from 'jsonwebtoken';
import AddressController from '../controllers/addressController';
import PaymentController from '../controllers/paymentController';

export const register = async (req: Request, res: Response) => {
  const {
    userName: UserName,
    password: PassWord,
    email: Email,
    name: Name,
    address: Address,
    zipCodeId: ZipCodeId,
    bankName: BankName,
    bankNum: BankNum,
    bankBranch: BankBranch
  } = req.body;  

  try {
    const existingUser = await UserModel.findByEmail(Email);
    if (existingUser) {
      return res.status(400).json({ message: 'User already exists' });
    }

    const addressData = {
      Name: Address,
      ZipCodeId: ZipCodeId
    };
    const addressResult = await AddressController.createAddress(addressData);
    const AddressId = addressResult.addressId;

    const hashedPassword = await bcrypt.hash(PassWord, 10);
    const newUser = {
      UserName,
      PassWord: hashedPassword,
      Avatar: '',
      Name,
      DateOfBirth: undefined,
      Gender: undefined,
      Phone: '',
      Email,
      AddressId
    };
    const userId = await UserModel.create(newUser);

    const paymentData = {
      UserId: userId,
      BankName,
      BankNum,
      BankBranch
    };
    await PaymentController.createPayment(paymentData);

    await UserRoleModel.create({ UserId: userId });

    res.status(201).json({ message: 'User registered successfully' });
  } catch (err) {
    if (err instanceof Error) {
      res.status(500).json({ message: err.message });
    } else {
      res.status(500).json({ message: 'An unknown error occurred' });
    }
  }
};

export const login = async (req: Request, res: Response) => {
  const { email: Email, password: PassWord } = req.body

  try {
    const user = await UserModel.findByEmail(Email)
    if (!user) {
      return res.status(400).json({ message: 'Invalid credentials' })
    }

    const isMatch = await bcrypt.compare(PassWord, user.PassWord)
    if (!isMatch) {
      return res.status(400).json({ message: 'Invalid credentials' })
    }

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
  const { email: Email } = req.body

  try {
    const user = await UserModel.findByEmail(Email)
    if (!user) {
      return res.status(400).json({ message: 'User not found' })
    }

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

export const logout = async (req: Request, res: Response) => {
  try {
    // Assume the token has been deleted on the client side to mark the user as logged out
    
    res.json({ message: 'Logout successful' });
  } catch (err) {
    console.error('Logout Error:', err);
    res.status(500).json({ message: 'Internal Server Error' });
  }
};

export const getUserInfo = async (req: Request, res: Response) => {
  const { userId } = req.params;

  try {
    // Fetch user info
    const user = await UserModel.findById(userId);
    if (!user) {
      return res.status(404).json({ message: 'User not found' });
    }

    // Fetch address info if AddressId exists
    let address = null;
    if (user.AddressId) {
      const address: any = {};
      await AddressController.findAddressById(user.AddressId, address);
    }

    // Fetch payment info
    const payment: any = {};
    await PaymentController.findPaymentById(user.UserId, payment);

    // Construct response
    const response = {
      user,
      address,
      payment,
    };

    res.json(response);
  } catch (err) {
    console.error('Error in UserController.getUserInfo:', err);
    res.status(500).json({ message: 'Failed to fetch user information' });
  }
};

export const updateUser = async (req: Request, res: Response) => {
  const {
    userId: UserId,
    userName: UserName,
    password: PassWord,
    avatar: Avatar,
    name: Name,
    dateOfBirth: DateOfBirth,
    gender: Gender,
    phone: Phone,
    email: Email,
    addressName: AddressName,
    zipCodeId: ZipCodeId,
    bankName: BankName,
    bankNum: BankNum,
    bankBranch: BankBranch
  } = req.body;

  try {
    const user = await UserModel.findById(UserId);
    if (!user) {
      return res.status(404).json({ message: 'User not found' });
    }

    const userUpdates: any = {};
    if (UserName) userUpdates.UserName = UserName;
    if (PassWord) userUpdates.PassWord = await bcrypt.hash(PassWord, 10);
    if (Avatar) userUpdates.Avatar = Avatar;
    if (Name) userUpdates.Name = Name;
    if (DateOfBirth) userUpdates.DateOfBirth = DateOfBirth;
    if (Gender) userUpdates.Gender = Gender;
    if (Phone) userUpdates.Phone = Phone;
    if (Email) userUpdates.Email = Email;

    if (Object.keys(userUpdates).length) {
      await UserModel.updateById(UserId, userUpdates);
    }

    const addressUpdates: any = {};
    if (AddressName) addressUpdates.Name = AddressName;
    if (ZipCodeId) addressUpdates.ZipCodeId = ZipCodeId;

    if (Object.keys(addressUpdates).length && user.AddressId) {
      await AddressController.updateAddress(user.AddressId, addressUpdates);
    }

    const paymentUpdates: any = {};
    if (BankName) paymentUpdates.BankName = BankName;
    if (BankNum) paymentUpdates.BankNum = BankNum;
    if (BankBranch) paymentUpdates.BankBranch = BankBranch;

    if (Object.keys(paymentUpdates).length) {
      await PaymentController.updatePayment(user.UserId, paymentUpdates);
    }

    const updatedUser = await UserModel.findById(UserId); // Fetch the updated user data

    res.json({ message: 'User updated successfully', user: updatedUser });
  } catch (err) {
    if (err instanceof Error) {
      res.status(500).json({ message: err.message });
    } else {
      res.status(500).json({ message: 'An unknown error occurred' });
    }
  }
};