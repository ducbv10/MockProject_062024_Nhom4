import { poolPromise } from '../config/db';

interface Address {
  AddressId?: string;
  Name: string;
  ZipCodeId: string;
}

class AddressModel {
  static async create(address: Address) {
    const pool = await poolPromise();
    try {
      const request = pool.request();
      request.input('Name', address.Name);
      request.input('ZipCodeId', address.ZipCodeId);

      const query = `
        INSERT INTO Address (Name, ZipCodeId)
        OUTPUT INSERTED.AddressId
        VALUES (@Name, @ZipCodeId);
      `;
      
      const result = await request.query(query);
      return result.recordset[0].AddressId;
    } catch (err) {
      console.error('Error in AddressModel.create:', err);
      throw err;
    }
  }

  static async findAll() {
    const pool = await poolPromise();
    try {
      const request = pool.request();

      const query = 'SELECT AddressId, Name, ZipCodeId FROM Address';
      const result = await request.query(query);
      return result.recordset;
    } catch (err) {
      console.error('Error in AddressModel.findAll:', err);
      throw err;
    }
  }

  static async findById(addressId: string) {
    const pool = await poolPromise();
    try {
      const request = pool.request();
      request.input('AddressId', addressId);

      const query = 'SELECT AddressId, Name, ZipCodeId FROM Address WHERE AddressId = @AddressId';
      const result = await request.query(query);
      return result.recordset[0];
    } catch (err) {
      console.error('Error in AddressModel.findById:', err);
      throw err;
    }
  }

  static async update(addressId: string, updatedFields: Partial<Address>) {
    const pool = await poolPromise();
    try {
      const request = pool.request();
      request.input('AddressId', addressId);
      request.input('Name', updatedFields.Name);
      request.input('ZipCodeId', updatedFields.ZipCodeId);

      const query = `
        UPDATE Address
        SET Name = @Name, ZipCodeId = @ZipCodeId
        WHERE AddressId = @AddressId
      `;

      await request.query(query);
    } catch (err) {
      console.error('Error in AddressModel.update:', err);
      throw err;
    }
  }

  static async delete(addressId: string) {
    const pool = await poolPromise();
    try {
      const request = pool.request();
      request.input('AddressId', addressId);

      const query = 'DELETE FROM Address WHERE AddressId = @AddressId';
      await request.query(query);
    } catch (err) {
      console.error('Error in AddressModel.delete:', err);
      throw err;
    }
  }
}

export default AddressModel;