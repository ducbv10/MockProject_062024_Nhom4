import { Request, Response } from 'express';
import StateModel from '../models/state';
import CityModel from '../models/city';
import ZipCodeModel from '../models/zipcode';

export const getStates = async (req: Request, res: Response) => {
    try {
        const states = await StateModel.findAll();
        res.status(200).json(states);
    } catch (err) {
        if (err instanceof Error) {
            res.status(500).json({ message: err.message });
        } else {
            res.status(500).json({ message: 'An unknown error occurred' });
        }
    }
};

export const getCitiesByState = async (req: Request, res: Response) => {
    const { stateId } = req.params;
    try {
        const cities = await CityModel.findAllByState(stateId);
        res.status(200).json(cities);
    } catch (err) {
        if (err instanceof Error) {
            res.status(500).json({ message: err.message });
        } else {
            res.status(500).json({ message: 'An unknown error occurred' });
        }
    }
};

export const getZipCodesByCity = async (req: Request, res: Response) => {
    const { cityId } = req.params;
    try {
        const zipcodes = await ZipCodeModel.findAllByCity(cityId);
        res.status(200).json(zipcodes);
    } catch (err) {
        if (err instanceof Error) {
            res.status(500).json({ message: err.message });
        } else {
            res.status(500).json({ message: 'An unknown error occurred' });
        }
    }
};