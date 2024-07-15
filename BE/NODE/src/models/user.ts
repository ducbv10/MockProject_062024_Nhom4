import { poolPromise } from '../config/db';
import { ConnectionPool, Request, VarChar, Date as SqlDate, DateTime } from 'mssql';

interface User {
  UserId: string;
  UserName: string;
  PassWord: string;
  Avatar?: string;
  Name?: string;
  DateOfBirth?: Date;
  Gender?: 'male' | 'female';
  Phone?: string;
  Email: string;
  AddressId?: string;
  IsBan: 'baned' | 'unbanned';
  Verify: 'verified' | 'unverified';
  DeletedAt?: Date;
}

class UserModel {
  static async create(user: User) {
    const pool: ConnectionPool = await poolPromise();
    const request: Request = pool.request();
    request.input('UserId', VarChar, user.UserId);
    request.input('UserName', VarChar, user.UserName);
    request.input('PassWord', VarChar, user.PassWord);
    request.input('Avatar', VarChar, user.Avatar ?? '');
    request.input('Name', VarChar, user.Name ?? '');
    request.input('DateOfBirth', SqlDate, user.DateOfBirth ?? undefined);
    request.input('Gender', VarChar, user.Gender ?? '');
    request.input('Phone', VarChar, user.Phone ?? '');
    request.input('Email', VarChar, user.Email);
    request.input('AddressId', VarChar, user.AddressId ?? '');
    request.input('IsBan', VarChar, user.IsBan);
    request.input('Verify', VarChar, user.Verify);
    request.input('DeletedAt', DateTime, user.DeletedAt ?? undefined);

    const query = `
      INSERT INTO [User] (UserId, UserName, PassWord, Avatar, Name, DateOfBirth, Gender, Phone, Email, AddressId, IsBan, Verify, DeletedAt)
      VALUES (@UserId, @UserName, @PassWord, @Avatar, @Name, @DateOfBirth, @Gender, @Phone, @Email, @AddressId, @IsBan, @Verify, @DeletedAt)
    `;

    return request.query(query);
  }

  static async findByEmail(email: string) {
    const pool: ConnectionPool = await poolPromise();
    const request: Request = pool.request();
    request.input('Email', VarChar, email);

    const query = 'SELECT * FROM [User] WHERE Email = @Email';
    const result = await request.query(query);
    return result.recordset[0];
  }
}

export default UserModel;
