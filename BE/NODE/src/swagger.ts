const PORT = process.env.PORT || 5000;
const swaggerOptions = {
  definition: {
    openapi: '3.0.0',
    info: {
      title: 'MockProject_062024_Nhom4 US Auction API',
      version: '1.0.0',
      description: 'US Auction API',
    },
    servers: [
      {
        url: `http://localhost:${PORT}/api/v1`,
        description: 'Development server',
      },
    ],
    components: {
      securitySchemes: {
        BearerAuth: {
          type: 'http',
          scheme: 'bearer',
          bearerFormat: 'JWT',
        },
      },
    },
  },
  apis: ['./src/routes/*.ts'],
};

export default swaggerOptions;