import express from 'express';
import { getAllRoles, getRoleById } from '../controllers/roleManagementController';
import authAdminMiddleware from '../middleware/authAdminMiddleware';

const router = express.Router();

/**
 * @swagger
 * tags:
 *   name: Admin - Role Management
 *   description: APIs for managing role data
 */

/**
 * @swagger
 * /admin/role-management:
 *   get:
 *     summary: Get all roles
 *     tags: [Admin - Role Management]
 *     security:
 *       - BearerAuth: []
 *     responses:
 *       200:
 *         description: List of all roles
 *       500:
 *         description: Error retrieving roles
 */
router.get('/', authAdminMiddleware, getAllRoles);

/**
 * @swagger
 * /admin/role-management/{roleId}:
 *   get:
 *     summary: Get role by ID
 *     tags: [Admin - Role Management]
 *     security:
 *       - BearerAuth: []
 *     parameters:
 *       - in: path
 *         name: roleId
 *         required: true
 *         schema:
 *           type: string
 *     responses:
 *       200:
 *         description: Role details
 *       404:
 *         description: Role not found
 *       500:
 *         description: Error retrieving role
 */
router.get('/:roleId', authAdminMiddleware, getRoleById);

export default router;