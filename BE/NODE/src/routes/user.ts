import { Router } from 'express';
import { register, login, forgotPassword, logout, updateUser, getUserInfo } from '../controllers/userController';
import authMiddleware from '../middleware/authMiddleware';

const router = Router();

/**
 * @swagger
 * tags:
 *   name: User
 *   description: APIs for managing user authentication
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
 *               userName:
 *                 type: string
 *               password:
 *                 type: string
 *               email:
 *                 type: string
 *               name:
 *                 type: string
 *               address:
 *                 type: string
 *               zipCodeId:
 *                 type: string
 *               bankName:
 *                 type: string
 *               bankNum:
 *                 type: string
 *               bankBranch:
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
 *               email:
 *                 type: string
 *               password:
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
 *               email:
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

/**
 * @swagger
 * /user/info/{userId}:
 *   get:
 *     summary: Get user information
 *     tags: [User]
 *     parameters:
 *       - in: path
 *         name: userId
 *         required: true
 *         schema:
 *           type: string
 *         description: The ID of the user
 *     responses:
 *       200:
 *         description: Successfully fetched user information
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 user:
 *                   type: object
 *                   properties:
 *                     UserId:
 *                       type: string
 *                     UserName:
 *                       type: string
 *                     PassWord:
 *                       type: string
 *                     Avatar:
 *                       type: string
 *                     Name:
 *                       type: string
 *                     DateOfBirth:
 *                       type: string
 *                       format: date-time
 *                     Gender:
 *                       type: string
 *                     Phone:
 *                       type: string
 *                     Email:
 *                       type: string
 *                     AddressId:
 *                       type: string
 *                     IsBan:
 *                       type: string
 *                     Verify:
 *                       type: string
 *                     DeletedAt:
 *                       type: string
 *                       format: date-time
 *                 address:
 *                   type: object
 *                   properties:
 *                     AddressId:
 *                       type: string
 *                     Name:
 *                       type: string
 *                     ZipCodeId:
 *                       type: string
 *                 payment:
 *                   type: object
 *                   properties:
 *                     PaymentId:
 *                       type: string
 *                     UserId:
 *                       type: string
 *                     BankName:
 *                       type: string
 *                     BankNum:
 *                       type: string
 *                     BankBranch:
 *                       type: string
 *                     AccountBalance:
 *                       type: number
 *       404:
 *         description: User not found
 *       500:
 *         description: Internal server error
 */

router.get('/info/:userId', authMiddleware, getUserInfo);

/**
 * @swagger
 * /user/update:
 *   put:
 *     summary: Update an existing user
 *     tags: [User]
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               userId:
 *                 type: string
 *               userName:
 *                 type: string
 *               password:
 *                 type: string
 *               avatar:
 *                 type: string
 *               name:
 *                 type: string
 *               dateOfBirth:
 *                 type: string
 *                 format: date
 *               gender:
 *                 type: string
 *                 enum: [male, female]
 *               phone:
 *                 type: string
 *               email:
 *                 type: string
 *               addressName:
 *                 type: string
 *               zipCodeId:
 *                 type: string
 *               bankName:
 *                 type: string
 *               bankNum:
 *                 type: string
 *               bankBranch:
 *                 type: string
 *     responses:
 *       200:
 *         description: User updated successfully
 *       404:
 *         description: User not found
 *       500:
 *         description: Internal server error
 */

router.put('/update', authMiddleware, updateUser);

export default router;