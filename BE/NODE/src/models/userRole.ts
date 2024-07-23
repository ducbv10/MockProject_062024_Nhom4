import { poolPromise } from '../config/db';
import { ConnectionPool, Request, VarChar, Date as SqlDate, DateTime } from 'mssql';

interface UserRole {
  UserId: string;
  RoleId?: string;
}

class UserRoleModel {
  static async findAll() {
    const pool = await poolPromise();
    try {
      const request = pool.request();

      const query = 'SELECT UserId, RoleId FROM UserRole';
      const result = await request.query(query);
      return result.recordset;
    } catch (err) {
      console.error('Error in UserRoleModel.findAll:', err);
      throw err;
    }
  }

  static async findByUserId(userId: string) {
    const pool = await poolPromise();
    try {
      const request = pool.request();
      request.input('UserId', VarChar, userId);

      const query = 'SELECT RoleId FROM UserRole WHERE UserId = @UserId';
      const result = await request.query(query);
      return result.recordset[0];
    } catch (err) {
      console.error('Error in UserRoleModel.findByUserId:', err);
      throw err;
    }
  }

  static async findByRoleId(roleId: string) {
    const pool = await poolPromise();
    try {
      const request = pool.request();
      request.input('RoleId', VarChar, roleId);

      const query = 'SELECT UserId, RoleId FROM UserRole WHERE RoleId = @RoleId';
      const result = await request.query(query);
      return result.recordset;
    } catch (err) {
      console.error('Error in UserRoleModel.findByRoleId:', err);
      throw err;
    }
  }

  static async create(userRole: UserRole) {
    const pool = await poolPromise();
    try {
      const request = pool.request();
      request.input('UserId', VarChar, userRole.UserId);
      
      const query = 'INSERT INTO UserRole (UserId) VALUES (@UserId)';
      await request.query(query);

      return { UserId: userRole.UserId, RoleId: userRole.RoleId };
    } catch (err) {
      console.error('Error in UserRoleModel.create:', err);
      throw err;
    }
  }

  static async updateRole(userId: string, newRoleId: string) {
    const pool = await poolPromise();
    try {
      const request = pool.request();
      request.input('UserId', VarChar, userId);
      request.input('NewRoleId', VarChar, newRoleId);
      request.input('DeletedAt', DateTime, new Date());

      // Soft delete existing roles for this user
      await request.query('UPDATE UserRole SET DeletedAt = @DeletedAt WHERE UserId = @UserId AND DeletedAt IS NULL');

      // Insert the new role
      const query = 'INSERT INTO UserRole (UserId, RoleId) VALUES (@UserId, @NewRoleId)';
      await request.query(query);

      return { UserId: userId, RoleId: newRoleId };
    } catch (err) {
      console.error('Error in UserRoleModel.updateRole:', err);
      throw err;
    }
  }
}

export default UserRoleModel;