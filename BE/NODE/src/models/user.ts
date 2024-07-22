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
  IsBan?: 'banned' | 'unbanned';
  Verify?: 'verified' | 'unverified';
  DeletedAt?: Date;
}

async function getLastUserId(): Promise<string> {
  const pool = await poolPromise();
  try {
    const request = pool.request();
    const query = `
      SELECT TOP 1 UserId
      FROM [User]
      ORDER BY UserId DESC;
    `;
    const result = await request.query(query);
    if (result.recordset.length > 0) {
      return result.recordset[0].UserId;
    } else {
      return 'US00000000'; // Return a default starting value if no users are found
    }
  } catch (err) {
    console.error('Error in getLastUserId:', err);
    throw err;
  }
}

class UserModel {
  static async create(user: User) {
    const pool = await poolPromise();
    const lastUserId = await getLastUserId();
    let nextUserId = 'US00000000';

    if (lastUserId) {
      const lastNum = parseInt(lastUserId.slice(2), 10);
      nextUserId = `US${(lastNum + 1).toString().padStart(8, '0')}`;
    }

    try {
      const request = pool.request();
      request.input('UserId', VarChar, nextUserId);
      request.input('UserName', VarChar, user.UserName);
      request.input('PassWord', VarChar, user.PassWord);
      request.input('Name', VarChar, user.Name ?? user.UserName);
      request.input('Email', VarChar, user.Email);
      request.input('AddressId', VarChar, user.AddressId ?? '');

      const query = `
        INSERT INTO [User] (UserId, UserName, PassWord, Name, Email, AddressId)
        OUTPUT INSERTED.UserId
        VALUES (@UserId, @UserName, @PassWord, @Name, @Email, @AddressId);
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

  static async findAll() {
    const pool = await poolPromise();
    try {
      const query = `
        SELECT UserId, UserName, PassWord, Avatar, Name, DateOfBirth, Gender, Phone, Email, AddressId, IsBan, Verify
        FROM [User]
        WHERE DeletedAt IS NULL;
      `;
      const result = await (await pool).request().query(query);
      return result.recordset;
    } catch (err) {
      console.error('Error in UserModel.findAll:', err);
      throw err;
    }
  }

  static async updateById(userId: string, updates: Partial<User>) {
    const pool = await poolPromise();
    try {
      const request = pool.request();
      const updateFields = Object.keys(updates).map(key => `${key} = @${key}`).join(', ');

      Object.entries(updates).forEach(([key, value]) => {
        request.input(key, value);
      });
      request.input('UserId', VarChar, userId);

      const query = `
        UPDATE [User]
        SET ${updateFields}
        WHERE UserId = @UserId;
      `;
      const result = await request.query(query);
      return result.rowsAffected[0] > 0 ? await UserModel.findById(userId) : null;
    } catch (err) {
      console.error('Error in UserModel.updateById:', err);
      throw err;
    }
  }

  static async deleteById(userId: string) {
    const pool = await poolPromise();
    try {
      const request = pool.request();
      request.input('UserId', VarChar, userId);
      request.input('DeletedAt', DateTime, new Date());

      const query = `
        UPDATE [User]
        SET DeletedAt = @DeletedAt
        WHERE UserId = @UserId;
      `;
      const result = await request.query(query);
      return result.rowsAffected[0] > 0;
    } catch (err) {
      console.error('Error in UserModel.deleteById:', err);
      throw err;
    }
  }

  static async restoreById(userId: string) {
    const pool = await poolPromise();
    try {
      const request = pool.request();
      request.input('UserId', VarChar, userId);
      request.input('DeletedAt', DateTime, null);

      const query = `
        UPDATE [User]
        SET DeletedAt = @DeletedAt
        WHERE UserId = @UserId;
      `;
      const result = await request.query(query);
      return result.rowsAffected[0] > 0;
    } catch (err) {
      console.error('Error in UserModel.restoreById:', err);
      throw err;
    }
  }
}

export default UserModel;


// import { poolPromise } from '../config/db';
// import { ConnectionPool, Request, VarChar, Date as SqlDate, DateTime } from 'mssql';

// interface User {
//   UserId?: string;
//   UserName: string;
//   PassWord: string;
//   Avatar?: string;
//   Name: string;
//   DateOfBirth?: Date;
//   Gender?: 'male' | 'female';
//   Phone?: string;
//   Email: string;
//   AddressId?: string;
//   IsBan?: 'banned' | 'unbanned';
//   Verify?: 'verified' | 'unverified';
//   DeletedAt?: Date;
// }

// async function getLastUserId(): Promise<string> {
//   const pool = await poolPromise();
//   try {
//     const request = pool.request();
//     const query = `
//       SELECT TOP 1 UserId
//       FROM [User]
//       ORDER BY UserId DESC;
//     `;
//     const result = await request.query(query);
//     if (result.recordset.length > 0) {
//       return result.recordset[0].UserId;
//     } else {
//       return 'US00000000'; // Return a default starting value if no users are found
//     }
//   } catch (err) {
//     console.error('Error in getLastUserId:', err);
//     throw err;
//   }
// }

// class UserModel {
//   static async create(user: User) {
//     const pool = await poolPromise();
//     const lastUserId = await getLastUserId();
//     let nextUserId = 'US00000000';

//     if (lastUserId) {
//       const lastNum = parseInt(lastUserId.slice(2), 10);
//       nextUserId = `US${(lastNum + 1).toString().padStart(8, '0')}`;
//     }

//     try {
//       const request = pool.request();
//       request.input('UserId', VarChar, nextUserId);
//       request.input('UserName', VarChar, user.UserName);
//       request.input('PassWord', VarChar, user.PassWord);
//       request.input('Name', VarChar, user.Name ?? user.UserName);
//       request.input('Email', VarChar, user.Email);
//       request.input('AddressId', VarChar, user.AddressId ?? '');

//       const query = `
//         INSERT INTO [User] (UserId, UserName, PassWord, Name, Email, AddressId)
//         OUTPUT INSERTED.UserId
//         VALUES (@UserId, @UserName, @PassWord, @Name, @Email, @AddressId);
//       `;
      
//       const result = await request.query(query);
//       return result.recordset[0].UserId;
//     } catch (err) {
//       console.error('Error in UserModel.create:', err);
//       throw err;
//     }
//   }

//   static async findByEmail(email: string) {
//     const pool = await poolPromise();
//     try {
//       const request = pool.request();
//       request.input('Email', VarChar, email);

//       const query = `
//             SELECT UserId, UserName, PassWord, Avatar, Name, DateOfBirth, Gender, Phone, Email, AddressId, IsBan, Verify
//             FROM [User]
//             WHERE Email = @Email AND DeletedAt IS NULL
//         `;
//       const result = await request.query(query);
//       return result.recordset[0];
//     } catch (err) {
//       console.error('Error in UserModel.findByEmail:', err);
//       throw err;
//     }
//   }

//   static async findById(userId: string) {
//     const pool = await poolPromise();
//     try {
//       const request = pool.request();
//       request.input('UserId', VarChar, userId);

//       const query = `
//             SELECT UserId, UserName, PassWord, Avatar, Name, DateOfBirth, Gender, Phone, Email, AddressId, IsBan, Verify
//             FROM [User]
//             WHERE UserId = @UserId AND DeletedAt IS NULL
//         `;
//       const result = await request.query(query);
//       return result.recordset[0];
//     } catch (err) {
//       console.error('Error in UserModel.findById:', err);
//       throw err;
//     }
//   }
// }

// export default UserModel;