import { ConnectionPool } from 'mssql';
import dotenv from 'dotenv';

dotenv.config();

const config: any = {
  user: process.env.DB_USER,
  password: process.env.DB_PASSWORD,
  server: process.env.DB_SERVER,
  database: process.env.DB_NAME,
  options: {
    encrypt: true,
    trustServerCertificate: true,
  },
};

const poolPromise = async () => {
  try {
    const pool = await new ConnectionPool(config).connect();
    console.log('Connected to MSSQL');
    return pool;
  } catch (err) {
    console.error('Database Connection Failed! Bad Config:', err);
    throw err;
  }
};

export { poolPromise };