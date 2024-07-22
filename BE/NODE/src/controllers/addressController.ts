import { Request, Response } from 'express';
import AddressModel from '../models/address';

class AddressController {
  static async createAddress(addressData: { Name: string, ZipCodeId: string }) {
    const { Name, ZipCodeId } = addressData;

    try {
      const newAddress = {
        Name,
        ZipCodeId
      };

      const result = await AddressModel.create(newAddress);
      return { addressId: result, message: 'Address created successfully' };
    } catch (err) {
      console.error('Error in AddressController.createAddress:', err);
      throw new Error('Failed to create address');
    }
  }

  static async findAllAddresses(req: Request, res: Response) {
    try {
      const addresses = await AddressModel.findAll();
      res.json(addresses);
    } catch (err) {
      console.error('Error in AddressController.findAllAddresses:', err);
      res.status(500).json({ message: 'Failed to fetch addresses' });
    }
  }

  static async findAddressById(req: Request, res: Response) {
    const { addressId } = req.params;

    try {
      const address = await AddressModel.findById(addressId);
      if (!address) {
        return res.status(404).json({ message: 'Address not found' });
      }
      res.json(address);
    } catch (err) {
      console.error('Error in AddressController.findAddressById:', err);
      res.status(500).json({ message: 'Failed to fetch address' });
    }
  }

  static async updateAddress(req: Request, res: Response) {
    const { addressId } = req.params;
    const { Name, ZipCodeId } = req.body;

    try {
      await AddressModel.update(addressId, { Name, ZipCodeId });
      res.json({ message: 'Address updated successfully' });
    } catch (err) {
      console.error('Error in AddressController.updateAddress:', err);
      res.status(500).json({ message: 'Failed to update address' });
    }
  }

  static async deleteAddress(req: Request, res: Response) {
    const { addressId } = req.params;

    try {
      await AddressModel.delete(addressId);
      res.json({ message: 'Address deleted successfully' });
    } catch (err) {
      console.error('Error in AddressController.deleteAddress:', err);
      res.status(500).json({ message: 'Failed to delete address' });
    }
  }
}

export default AddressController;