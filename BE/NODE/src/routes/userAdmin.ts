import { Router } from 'express';
import { login, logout } from '../controllers/userAdminController';
import authAdminMiddleware from '../middleware/authAdminMiddleware';
import { getUserInfo, updateUser } from '../controllers/userController';

const router = Router();

/**
 * @swagger
 * tags:
 *   name: Admin
 *   description: APIs for managing admin user data
 */

/**
 * @swagger
 * /admin/login:
 *   post:
 *     summary: Admin login
 *     tags: [Admin]
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
 *         description: Admin logged in successfully
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 token:
 *                   type: string
 *       400:
 *         description: Invalid credentials
 *       500:
 *         description: Internal server error
 */
router.post('/login', login);

/**
 * @swagger
 * /admin/logout:
 *   post:
 *     summary: Admin logout
 *     description: |
 *       Invalidate the current JWT token and log the admin out.
 *       Make sure to include your JWT token in the Authorization header as `Bearer <your_token_here>`.
 *     tags: [Admin]
 *     security:
 *       - BearerAuth: []
 *     responses:
 *       200:
 *         description: Logout successful
 *       500:
 *         description: Internal server error
 */
router.post('/logout', authAdminMiddleware, logout);

/**
 * @swagger
 * /admin/info/{userId}:
 *   get:
 *     summary: Get user information
 *     tags: [Admin]
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
 *                     userId:
 *                       type: string
 *                     userName:
 *                       type: string
 *                     password:
 *                       type: string
 *                     avatar:
 *                       type: string
 *                     name:
 *                       type: string
 *                     dateOfBirth:
 *                       type: string
 *                       format: date-time
 *                     gender:
 *                       type: string
 *                     phone:
 *                       type: string
 *                     email:
 *                       type: string
 *                     addressId:
 *                       type: string
 *                     isBan:
 *                       type: string
 *                     verify:
 *                       type: string
 *                     deletedAt:
 *                       type: string
 *                       format: date-time
 *                 address:
 *                   type: object
 *                   properties:
 *                     addressId:
 *                       type: string
 *                     name:
 *                       type: string
 *                     zipCodeId:
 *                       type: string
 *                 payment:
 *                   type: object
 *                   properties:
 *                     paymentId:
 *                       type: string
 *                     userId:
 *                       type: string
 *                     bankName:
 *                       type: string
 *                     bankNum:
 *                       type: string
 *                     bankBranch:
 *                       type: string
 *                     accountBalance:
 *                       type: number
 *       404:
 *         description: User not found
 *       500:
 *         description: Internal server error
 */
router.get('/info/:userId', authAdminMiddleware, getUserInfo);

/**
 * @swagger
 * /admin/info/update:
 *   put:
 *     summary: Update an existing user
 *     tags: [Admin]
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
router.put('/info/update', authAdminMiddleware, updateUser);

export default router;