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
        url: 'http://localhost:5000/api',
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