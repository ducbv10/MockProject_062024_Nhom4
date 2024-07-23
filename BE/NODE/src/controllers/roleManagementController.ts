import { Request, Response } from 'express';
import RoleModel from '../models/role';

export const getAllRoles = async (req: Request, res: Response) => {
  try {
    const roles = await RoleModel.findAll();
    res.json({ roles });
  } catch (err) {
    res.status(500).json({ message: 'Error retrieving roles', error: err });
  }
};

export const getRoleById = async (req: Request, res: Response) => {
  const { roleId } = req.params;

  try {
    const role = await RoleModel.findById(roleId);
    if (!role) {
      return res.status(404).json({ message: 'Role not found' });
    }
    res.json({ role });
  } catch (err) {
    res.status(500).json({ message: 'Error retrieving role', error: err });
  }
};