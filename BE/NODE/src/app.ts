import express from 'express';
import dotenv from 'dotenv';
import userRoutes from './routes/user';
import locationRoutes from './routes/location';
import userAdminRoutes from './routes/userAdmin';
import userManagementRoutes from './routes/userManagement';
import roleManagementRoutes from './routes/roleManagement';
import swaggerUi from 'swagger-ui-express';
import swaggerJsdoc from 'swagger-jsdoc';
import swaggerOptions from './swagger';
import { poolPromise } from './config/db';

dotenv.config();

const app = express();

// Middleware
app.use(express.json());

// Routes
app.use('/api/v1/user', userRoutes);
app.use('/api/v1/location', locationRoutes);
app.use('/api/v1/admin', userAdminRoutes);
app.use('/api/v1/admin/user-management', userManagementRoutes);
app.use('/api/v1/admin/role-management', roleManagementRoutes);

// Swagger API documentation
const specs = swaggerJsdoc(swaggerOptions);
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(specs));

// Start the server
const PORT = process.env.PORT || 5000;
app.listen(PORT, () => {
  console.log(`Server is running on http://localhost:${PORT}`);

  poolPromise().catch(err => {
    console.error('Failed to connect to the database:', err);
  });
});