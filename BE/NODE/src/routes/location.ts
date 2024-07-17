import express from 'express';
import { getStates, getCitiesByState, getZipCodesByCity } from '../controllers/locationController';

const router = express.Router();

/**
 * @swagger
 * tags:
 *   name: Location
 *   description: APIs for managing location data
 */

/**
 * @swagger
 * /location/states:
 *   get:
 *     summary: Get all states
 *     tags: [Location]
 *     responses:
 *       200:
 *         description: A list of states
 *         content:
 *           application/json:
 *             schema:
 *               type: array
 *               items:
 *                 type: object
 *                 properties:
 *                   StateId:
 *                     type: string
 *                   Name:
 *                     type: string
 *       500:
 *         description: Internal server error
 */
router.get('/states', getStates);

/**
 * @swagger
 * /location/cities/{stateId}:
 *   get:
 *     summary: Get cities by state
 *     tags: [Location]
 *     parameters:
 *       - in: path
 *         name: stateId
 *         required: true
 *         schema:
 *           type: string
 *     responses:
 *       200:
 *         description: A list of cities
 *         content:
 *           application/json:
 *             schema:
 *               type: array
 *               items:
 *                 type: object
 *                 properties:
 *                   CityId:
 *                     type: string
 *                   Name:
 *                     type: string
 *       500:
 *         description: Internal server error
 */
router.get('/cities/:stateId', getCitiesByState);

/**
 * @swagger
 * /location/zipcodes/{cityId}:
 *   get:
 *     summary: Get zip codes by city
 *     tags: [Location]
 *     parameters:
 *       - in: path
 *         name: cityId
 *         required: true
 *         schema:
 *           type: string
 *     responses:
 *       200:
 *         description: A list of zip codes
 *         content:
 *           application/json:
 *             schema:
 *               type: array
 *               items:
 *                 type: object
 *                 properties:
 *                   ZipCodeId:
 *                     type: string
 *                   Name:
 *                     type: string
 *       500:
 *         description: Internal server error
 */
router.get('/zipcodes/:cityId', getZipCodesByCity);

export default router;