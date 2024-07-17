import { poolPromise } from '../config/db';
import { ConnectionPool, Request, VarChar } from 'mssql';

interface City {
  CityId: string;
  Name: string;
  StateId: string;
  DeletedAt?: Date;
}

class CityModel {
  static async create(city: City) {
    const pool = await poolPromise();
    try {
      const request = pool.request();
      request.input('CityId', VarChar, city.CityId);
      request.input('Name', VarChar, city.Name);
      request.input('StateId', VarChar, city.StateId);

      const query = `
        INSERT INTO City (CityId, Name, StateId)
        VALUES (@CityId, @Name, @StateId)
      `;

      await request.query(query);
    } catch (err) {
      console.error('Error in CityModel.create:', err);
      throw err;
    }
  }

  static async findAllByState(stateId: string) {
    const pool = await poolPromise();
    try {
      const request = pool.request();
      request.input('StateId', VarChar, stateId);

      const query = 'SELECT CityId, Name FROM City WHERE StateId = @StateId AND DeletedAt IS NULL';
      const result = await request.query(query);
      return result.recordset;
    } catch (err) {
      console.error('Error in CityModel.findAllByState:', err);
      throw err;
    }
  }

  static async findById(cityId: string) {
    const pool = await poolPromise();
    try {
      const request = pool.request();
      request.input('CityId', VarChar, cityId);

      const query = 'SELECT CityId, Name FROM City WHERE CityId = @CityId AND DeletedAt IS NULL';
      const result = await request.query(query);
      return result.recordset[0];
    } catch (err) {
      console.error('Error in CityModel.findById:', err);
      throw err;
    }
  }
}

export default CityModel;