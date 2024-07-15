import express from 'express'
import bodyParser from 'body-parser'
import dotenv from 'dotenv'
import userRoutes from './routes/user'
import swaggerUi from 'swagger-ui-express'
import swaggerJsdoc from 'swagger-jsdoc'
import swaggerOptions from './swagger'

dotenv.config()

const app = express()

app.use(bodyParser.json())

// Routes
app.use('/api/users', userRoutes)

// Swagger API documentation
const specs = swaggerJsdoc(swaggerOptions)
app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(specs))

// Start the server
const PORT = process.env.PORT || 5000
app.listen(PORT, () => {
  console.log(`Server is running on http://localhost:${PORT}`)
})