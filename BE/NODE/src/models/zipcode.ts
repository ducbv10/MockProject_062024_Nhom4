import { poolPromise } from '../config/db';
import { ConnectionPool, Request, VarChar } from 'mssql';

interface ZipCode {
  ZipCodeId: string;
  Name: string;
  CityId: string;
  DeletedAt?: Date;
}

class ZipCodeModel {
  static async create(zipCode: ZipCode) {
    const pool = await poolPromise();
    try {
      const request = pool.request();
      request.input('ZipCodeId', VarChar, zipCode.ZipCodeId);
      request.input('Name', VarChar, zipCode.Name);
      request.input('CityId', VarChar, zipCode.CityId);

      const query = `
        INSERT INTO ZipCode (ZipCodeId, Name, CityId)
        VALUES (@ZipCodeId, @Name, @CityId)
      `;

      await request.query(query);
    } catch (err) {
      console.error('Error in ZipCodeModel.create:', err);
      throw err;
    }
  }

  static async findAllByCity(cityId: string) {
    const pool = await poolPromise();
    try {
      const request = pool.request();
      request.input('CityId', VarChar, cityId);

      const query = 'SELECT ZipCodeId, Name FROM ZipCode WHERE CityId = @CityId AND DeletedAt IS NULL';
      const result = await request.query(query);
      return result.recordset;
    } catch (err) {
      console.error('Error in ZipCodeModel.findAllByCity:', err);
      throw err;
    }
  }

  static async findById(zipCodeId: string) {
    const pool = await poolPromise();
    try {
      const request = pool.request();
      request.input('ZipCodeId', VarChar, zipCodeId);

      const query = 'SELECT ZipCodeId, Name FROM ZipCode WHERE ZipCodeId = @ZipCodeId AND DeletedAt IS NULL';
      const result = await request.query(query);
      return result.recordset[0];
    } catch (err) {
      console.error('Error in ZipCodeModel.findById:', err);
      throw err;
    }
  }
}

export default ZipCodeModel;