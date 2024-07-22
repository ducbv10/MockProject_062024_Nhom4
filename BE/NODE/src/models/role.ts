import { poolPromise } from '../config/db';
import { VarChar } from 'mssql';

interface Role {
  RoleId: string;
  Name: string;
}

class RoleModel {
  static async findAll() {
    const pool = await poolPromise();
    try {
      const request = pool.request();

      const query = 'SELECT RoleId, Name FROM Role';
      const result = await request.query(query);
      return result.recordset;
    } catch (err) {
      console.error('Error in RoleModel.findAll:', err);
      throw err;
    }
  }

  static async findById(roleId: string) {
    const pool = await poolPromise();
    try {
      const request = pool.request();
      request.input('RoleId', VarChar, roleId);

      const query = 'SELECT RoleId, Name FROM Role WHERE RoleId = @RoleId';
      const result = await request.query(query);
      return result.recordset[0];
    } catch (err) {
      console.error('Error in RoleModel.findById:', err);
      throw err;
    }
  }
}

export default RoleModel;