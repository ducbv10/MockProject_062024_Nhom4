import { Router } from 'express';
import { createUser, readUser, updateUser, deleteUser, restoreUser, findAllUsers, findByEmail, updateUserRole, updateIsBan, updateVerify } from '../controllers/userManagementController';
import authAdminMiddleware from '../middleware/authAdminMiddleware';

const router = Router();

/**
 * @swagger
 * tags:
 *   name: Admin - User Management
 *   description: APIs for managing user data
 */

/**
 * @swagger
 * /admin/user-management:
 *   post:
 *     summary: Create a new user
 *     tags: [Admin - User Management]
 *     security:
 *       - BearerAuth: []
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
 *               addressId:
 *                 type: string
 *     responses:
 *       201:
 *         description: User created successfully
 *       500:
 *         description: Error creating user
 */
router.post('/', authAdminMiddleware, createUser);

/**
 * @swagger
 * /admin/user-management/{userId}:
 *   get:
 *     summary: Get user details
 *     tags: [Admin - User Management]
 *     security:
 *       - BearerAuth: []
 *     parameters:
 *       - in: path
 *         name: userId
 *         required: true
 *         schema:
 *           type: string
 *     responses:
 *       200:
 *         description: User details
 *       404:
 *         description: User not found
 *       500:
 *         description: Error reading user
 */
router.get('/:userId', authAdminMiddleware, readUser);

/**
 * @swagger
 * /admin/user-management/{userId}:
 *   put:
 *     summary: Update user details
 *     tags: [Admin - User Management]
 *     security:
 *       - BearerAuth: []
 *     parameters:
 *       - in: path
 *         name: userId
 *         required: true
 *         schema:
 *           type: string
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
 *               addressId:
 *                 type: string
 *     responses:
 *       200:
 *         description: User updated successfully
 *       404:
 *         description: User not found
 *       500:
 *         description: Error updating user
 */
router.put('/:userId', authAdminMiddleware, updateUser);

/**
 * @swagger
 * /admin/user-management/{userId}:
 *   delete:
 *     summary: Delete a user
 *     tags: [Admin - User Management]
 *     security:
 *       - BearerAuth: []
 *     parameters:
 *       - in: path
 *         name: userId
 *         required: true
 *         schema:
 *           type: string
 *     responses:
 *       200:
 *         description: User deleted successfully
 *       404:
 *         description: User not found
 *       500:
 *         description: Error deleting user
 */
router.delete('/:userId', authAdminMiddleware, deleteUser);

/**
 * @swagger
 * /admin/user-management/restore/{userId}:
 *   post:
 *     summary: Restore a deleted user
 *     tags: [Admin - User Management]
 *     security:
 *       - BearerAuth: []
 *     parameters:
 *       - in: path
 *         name: userId
 *         required: true
 *         schema:
 *           type: string
 *     responses:
 *       200:
 *         description: User restored successfully
 *       404:
 *         description: User not found
 *       500:
 *         description: Error restoring user
 */
router.post('/restore/:userId', authAdminMiddleware, restoreUser);

/**
 * @swagger
 * /admin/user-management:
 *   get:
 *     summary: Get all users
 *     tags: [Admin - User Management]
 *     security:
 *       - BearerAuth: []
 *     responses:
 *       200:
 *         description: List of all users
 *       500:
 *         description: Error finding users
 */
router.get('/', authAdminMiddleware, findAllUsers);

/**
 * @swagger
 * /admin/user-management/email/{email}:
 *   get:
 *     summary: Get user by email
 *     tags: [Admin - User Management]
 *     security:
 *       - BearerAuth: []
 *     parameters:
 *       - in: path
 *         name: email
 *         required: true
 *         schema:
 *           type: string
 *     responses:
 *       200:
 *         description: User details
 *       404:
 *         description: User not found
 *       500:
 *         description: Error finding user
 */
router.get('/email/:email', authAdminMiddleware, findByEmail);

/**
 * @swagger
 * /admin/user-management/{userId}/role:
 *   patch:
 *     summary: Update user role
 *     tags: [Admin - User Management]
 *     security:
 *       - BearerAuth: []
 *     parameters:
 *       - in: path
 *         name: userId
 *         required: true
 *         schema:
 *           type: string
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               roleId:
 *                 type: string
 *     responses:
 *       200:
 *         description: User role updated successfully
 *       404:
 *         description: User not found
 *       500:
 *         description: Error updating user role
 */
router.patch('/:userId/role', authAdminMiddleware, updateUserRole);

/**
 * @swagger
 * /admin/user-management/{userId}/isBan:
 *   patch:
 *     summary: Update user isBan status
 *     tags: [Admin - User Management]
 *     security:
 *       - BearerAuth: []
 *     parameters:
 *       - in: path
 *         name: userId
 *         required: true
 *         schema:
 *           type: string
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               isBan:
 *                 type: boolean
 *     responses:
 *       200:
 *         description: User isBan status updated successfully
 *       404:
 *         description: User not found
 *       500:
 *         description: Error updating user isBan status
 */
router.patch('/:userId/isBan', authAdminMiddleware, updateIsBan);

/**
 * @swagger
 * /admin/user-management/{userId}/verify:
 *   patch:
 *     summary: Update user verify status
 *     tags: [Admin - User Management]
 *     security:
 *       - BearerAuth: []
 *     parameters:
 *       - in: path
 *         name: userId
 *         required: true
 *         schema:
 *           type: string
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             type: object
 *             properties:
 *               verify:
 *                 type: boolean
 *     responses:
 *       200:
 *         description: User verify status updated successfully
 *       404:
 *         description: User not found
 *       500:
 *         description: Error updating user verify status
 */
router.patch('/:userId/verify', authAdminMiddleware, updateVerify);

export default router;