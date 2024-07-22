import { Request, Response } from 'express';
import UserModel from '../models/user';
import UserRoleModel from '../models/userRole';
import bcrypt from 'bcryptjs';

export const createUser = async (req: Request, res: Response) => {
  const {
    userName: UserName,
    password: PassWord,
    email: Email,
    name: Name,
    addressId: AddressId
  } = req.body;

  try {
    const hashedPassword = await bcrypt.hash(PassWord, 10);
    const newUser = {
      UserName,
      PassWord: hashedPassword,
      Name,
      Email,
      AddressId
    };
    const userId = await UserModel.create(newUser);
    res.status(201).json({ message: 'User created successfully', userId });
  } catch (err) {
    res.status(500).json({ message: 'Error creating user', error: err });
  }
};

export const readUser = async (req: Request, res: Response) => {
  const { userId } = req.params;

  try {
    const user = await UserModel.findById(userId);
    if (!user) {
      return res.status(404).json({ message: 'User not found' });
    }
    res.json(user);
  } catch (err) {
    res.status(500).json({ message: 'Error reading user', error: err });
  }
};

export const updateUser = async (req: Request, res: Response) => {
  const { userId } = req.params;
  const updates = req.body;

  try {
    const updatedUser = await UserModel.updateById(userId, updates);
    if (!updatedUser) {
      return res.status(404).json({ message: 'User not found' });
    }
    res.json({ message: 'User updated successfully', updatedUser });
  } catch (err) {
    res.status(500).json({ message: 'Error updating user', error: err });
  }
};

export const deleteUser = async (req: Request, res: Response) => {
  const { userId } = req.params;

  try {
    const deletedUser = await UserModel.deleteById(userId);
    if (!deletedUser) {
      return res.status(404).json({ message: 'User not found' });
    }
    res.json({ message: 'User deleted successfully' });
  } catch (err) {
    res.status(500).json({ message: 'Error deleting user', error: err });
  }
};

export const restoreUser = async (req: Request, res: Response) => {
  const { userId } = req.params;

  try {
    const restoredUser = await UserModel.restoreById(userId);
    if (!restoredUser) {
      return res.status(404).json({ message: 'User not found' });
    }
    res.json({ message: 'User restored successfully' });
  } catch (err) {
    res.status(500).json({ message: 'Error restoring user', error: err });
  }
};

export const findAllUsers = async (req: Request, res: Response) => {
  try {
    const users = await UserModel.findAll();
    res.json(users);
  } catch (err) {
    res.status(500).json({ message: 'Error finding users', error: err });
  }
};

export const findByEmail = async (req: Request, res: Response) => {
  const { email } = req.params;

  try {
    const user = await UserModel.findByEmail(email);
    if (!user) {
      return res.status(404).json({ message: 'User not found' });
    }
    res.json(user);
  } catch (err) {
    res.status(500).json({ message: 'Error finding user', error: err });
  }
};

export const updateUserRole = async (req: Request, res: Response) => {
  const { userId } = req.params;
  const { roleId } = req.body;

  try {
    const user = await UserModel.findById(userId);
    if (!user) {
      return res.status(404).json({ message: 'User not found' });
    }

    const updatedRole = await UserRoleModel.updateRole(userId, roleId);
    res.json({ message: 'User role updated successfully', updatedRole });
  } catch (err) {
    res.status(500).json({ message: 'Error updating user role', error: err });
  }
};

export const updateIsBan = async (req: Request, res: Response) => {
  const { userId } = req.params;
  const { isBan } = req.body;

  const banStatus = isBan ? 'banned' : 'unbanned';

  try {
    const updatedUser = await UserModel.updateById(userId, { IsBan: banStatus });
    if (!updatedUser) {
      return res.status(404).json({ message: 'User not found' });
    }
    res.json({ message: 'User isBan status updated successfully', updatedUser });
  } catch (err) {
    res.status(500).json({ message: 'Error updating user isBan status', error: err });
  }
};

export const updateVerify = async (req: Request, res: Response) => {
  const { userId } = req.params;
  const { verify } = req.body;

  const verifyStatus = verify ? 'verified' : 'unverified';

  try {
    const updatedUser = await UserModel.updateById(userId, { Verify: verifyStatus });
    if (!updatedUser) {
      return res.status(404).json({ message: 'User not found' });
    }
    res.json({ message: 'User verify status updated successfully', updatedUser });
  } catch (err) {
    res.status(500).json({ message: 'Error updating user verify status', error: err });
  }
};