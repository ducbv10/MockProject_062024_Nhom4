import { Router } from 'express';
import { register, login, forgotPassword, logout } from '../controllers/userController';
import authMiddleware from '../middleware/authMiddleware';

const router = Router();

/**
 * @swagger
 * tags:
 *   name: User
 *   description: APIs for managing user data
 */

/**
 * @swagger
 * /user/register:
 *   post:
 *     summary: Register a new user
 *     tags: [User]
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               UserName:
 *                 type: string
 *               PassWord:
 *                 type: string
 *               Email:
 *                 type: string
 *               Name:
 *                 type: string
 *               Address:
 *                 type: string
 *               ZipCodeId:
 *                 type: string
 *               BankName:
 *                 type: string
 *               BankNum:
 *                 type: string
 *               BankBranch:
 *                 type: string
 *     responses:
 *       201:
 *         description: User registered successfully
 *       400:
 *         description: User already exists
 *       500:
 *         description: Internal server error
 */

router.post('/register', register);

/**
 * @swagger
 * /user/login:
 *   post:
 *     summary: Login a user
 *     tags: [User]
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               Email:
 *                 type: string
 *               PassWord:
 *                 type: string
 *     responses:
 *       200:
 *         description: User logged in successfully
 *       400:
 *         description: Invalid credentials
 *       500:
 *         description: Internal server error
 */
router.post('/login', login);

/**
 * @swagger
 * /user/forgot-password:
 *   post:
 *     summary: Forgot password
 *     tags: [User]
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               Email:
 *                 type: string
 *     responses:
 *       200:
 *         description: Password reset link has been sent to your email
 *       400:
 *         description: User not found
 *       500:
 *         description: Internal server error
 */
router.post('/forgot-password', forgotPassword);

/**
 * @swagger
 * /user/logout:
 *   post:
 *     summary: Logout a user
 *     description: |
 *       Invalidate the current JWT token and log the user out.
 *       Make sure to include your JWT token in the Authorization header as `Bearer <your_token_here>`.
 *     tags: [User]
 *     security:
 *       - BearerAuth: []
 *     responses:
 *       200:
 *         description: Logout successful
 *       401:
 *         description: Invalid token or token expired
 */
router.post('/logout', authMiddleware, logout);

export default router;