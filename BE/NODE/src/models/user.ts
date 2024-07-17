import { poolPromise } from '../config/db';
import { ConnectionPool, Request, VarChar, Date as SqlDate, DateTime } from 'mssql';

interface User {
  UserId?: string;
  UserName: string;
  PassWord: string;
  Avatar?: string;
  Name: string;
  DateOfBirth?: Date;
  Gender?: 'male' | 'female';
  Phone?: string;
  Email: string;
  AddressId?: string;
  IsBan?: 'baned' | 'unbanned';
  Verify?: 'verified' | 'unverified';
  DeletedAt?: Date;
}

class UserModel {
  static async create(user: User) {
    const pool = await poolPromise();
    try {
      const request = pool.request();
      request.input('UserName', VarChar, user.UserName);
      request.input('PassWord', VarChar, user.PassWord);
      request.input('Name', VarChar, user.Name ?? user.UserName);
      request.input('Email', VarChar, user.Email);
      request.input('AddressId', VarChar, user.AddressId ?? '');

      const query = `
        INSERT INTO [User] (UserName, PassWord, Name, Email, AddressId)
        OUTPUT INSERTED.UserId
        VALUES (@UserName, @PassWord, @Name, @Email, @AddressId);
      `;
      
      const result = await request.query(query);
      return result.recordset[0].UserId;
    } catch (err) {
      console.error('Error in UserModel.create:', err);
      throw err;
    }
  }

  static async findByEmail(email: string) {
    const pool = await poolPromise();
    try {
      const request = pool.request();
      request.input('Email', VarChar, email);

      const query = `
            SELECT UserId, UserName, PassWord, Avatar, Name, DateOfBirth, Gender, Phone, Email, AddressId, IsBan, Verify
            FROM [User]
            WHERE Email = @Email AND DeletedAt IS NULL
        `;
      const result = await request.query(query);
      return result.recordset[0];
    } catch (err) {
      console.error('Error in UserModel.findByEmail:', err);
      throw err;
    }
  }

  static async findById(userId: string) {
    const pool = await poolPromise();
    try {
      const request = pool.request();
      request.input('UserId', VarChar, userId);

      const query = `
            SELECT UserId, UserName, PassWord, Avatar, Name, DateOfBirth, Gender, Phone, Email, AddressId, IsBan, Verify
            FROM [User]
            WHERE UserId = @UserId AND DeletedAt IS NULL
        `;
      const result = await request.query(query);
      return result.recordset[0];
    } catch (err) {
      console.error('Error in UserModel.findById:', err);
      throw err;
    }
  }
}

export default UserModel;