import { DateTime, VarChar } from 'mssql';
import { poolPromise } from '../config/db';

interface Address {
  AddressId?: string;
  Name: string;
  ZipCodeId: string;
}

async function getLastAddressId(): Promise<string> {
  const pool = await poolPromise();
  try {
    const request = pool.request();
    const query = `
      SELECT TOP 1 AddressId
      FROM [Address]
      ORDER BY AddressId DESC;
    `;
    const result = await request.query(query);
    if (result.recordset.length > 0) {
      return result.recordset[0].AddressId;
    } else {
      return 'ADD0000000'; // Return a default starting value if no Addresss are found
    }
  } catch (err) {
    console.error('Error in getLastAddressId:', err);
    throw err;
  }
}

class AddressModel {
  static async create(address: Address) {
    const pool = await poolPromise();
    const lastAddressId = await getLastAddressId();
    let nextAddressId = 'ADD0000000';

    if (lastAddressId) {
      const lastNum = parseInt(lastAddressId.slice(3), 10);
      nextAddressId = `ADD${(lastNum + 1).toString().padStart(7, '0')}`;
    }

    try {
      const request = pool.request();
      request.input('AddressId', nextAddressId);
      request.input('Name', address.Name);
      request.input('ZipCodeId', address.ZipCodeId);

      const query = `
        INSERT INTO Address (AddressId, Name, ZipCodeId)
        OUTPUT INSERTED.AddressId
        VALUES (@AddressId, @Name, @ZipCodeId);
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
      const updateFields = Object.keys(updatedFields).map(key => `${key} = @${key}`).join(', ');

      Object.entries(updatedFields).forEach(([key, value]) => {
        request.input(key, value);
      });
      request.input('AddressId', VarChar, addressId);

      const query = `
            UPDATE Address
            SET ${updateFields}
            WHERE AddressId = @AddressId;
        `;
      const result = await request.query(query);
      return result.rowsAffected[0] > 0 ? await AddressModel.findById(addressId) : null;
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
      request.input('DeletedAt', DateTime, new Date());

      const query = `
        UPDATE Address
        SET DeletedAt = @DeletedAt
        WHERE AddressId = @AddressId;
      `;
      await request.query(query);
    } catch (err) {
      console.error('Error in AddressModel.delete:', err);
      throw err;
    }
  }
}

export default AddressModel;