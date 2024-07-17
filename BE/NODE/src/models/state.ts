import { poolPromise } from '../config/db';
import { ConnectionPool, Request, VarChar } from 'mssql';

interface State {
  StateId: string;
  Name: string;
}

class StateModel {
  static async create(state: State) {
    const pool = await poolPromise();
    try {
      const request = pool.request();
      request.input('StateId', VarChar, state.StateId);
      request.input('Name', VarChar, state.Name);

      const query = `
        INSERT INTO State (StateId, Name)
        VALUES (@StateId, @Name)
      `;
      
      await request.query(query);
    } catch (err) {
      console.error('Error in StateModel.create:', err);
      throw err;
    }
  }

  static async findAll() {
    const pool = await poolPromise();
    try {
      const request = pool.request();

      const query = 'SELECT StateId, Name FROM State WHERE DeletedAt IS NULL';
      const result = await request.query(query);
      return result.recordset;
    } catch (err) {
      console.error('Error in StateModel.findAll:', err);
      throw err;
    }
  }

  static async findById(stateId: string) {
    const pool = await poolPromise();
    try {
      const request = pool.request();
      request.input('StateId', VarChar, stateId);

      const query = 'SELECT StateId, Name FROM State WHERE StateId = @StateId AND DeletedAt IS NULL';
      const result = await request.query(query);
      return result.recordset[0];
    } catch (err) {
      console.error('Error in StateModel.findById:', err);
      throw err;
    }
  }
}

export default StateModel;