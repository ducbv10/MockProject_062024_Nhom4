<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use App\Models\Warehouse;
use Illuminate\Http\Request;

/**
 * @group Warehouses
 *
 * APIs for managing warehouses
 */
class WarehouseController extends Controller
{
    /**
     * Display a listing of the warehouses.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return Warehouse::all();
    }

    /**
     * Store a newly created warehouse in storage.
     *
     * @bodyParam Position string required The position of the warehouse. Example: A1
     * @bodyParam Quantity float required The quantity of items in the warehouse. Example: 100.5
     * @bodyParam importDate dateTime required The import date of items in the warehouse. Example: 2024-07-14 10:00:00
     * @bodyParam exportDate dateTime required The export date of items in the warehouse. Example: 2024-07-15 15:30:00
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $warehouse = Warehouse::create($request->all());
        return response()->json($warehouse, 201);
    }

    /**
     * Display the specified warehouse.
     *
     * @urlParam id string required The ID of the warehouse.
     *
     * @param  string  $WareHouseId
     * @return \Illuminate\Http\Response
     */
    public function show($WareHouseId)
    {
        $warehouse = Warehouse::findOrFail($WareHouseId);
        return response()->json($warehouse);
    }

    /**
     * Update the specified warehouse in storage.
     *
     * @urlParam id string required The ID of the warehouse.
     * @bodyParam Position string required The position of the warehouse. Example: Updated Position
     * @bodyParam Quantity float required The quantity of items in the warehouse. Example: 150.5
     * @bodyParam importDate dateTime required The import date of items in the warehouse. Example: 2024-07-15 09:00:00
     * @bodyParam exportDate dateTime required The export date of items in the warehouse. Example: 2024-07-16 14:00:00
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  string  $WareHouseId
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $WareHouseId)
    {
        $warehouse = Warehouse::findOrFail($WareHouseId);
        $warehouse->update($request->all());
        return response()->json($warehouse, 200);
    }

    /**
     * Remove the specified warehouse from storage.
     *
     * @urlParam id string required The ID of the warehouse.
     *
     * @param  string  $WareHouseId
     * @return \Illuminate\Http\Response
     */
    public function destroy($WareHouseId)
    {
        Warehouse::findOrFail($WareHouseId)->delete();
        return response()->json(['message' => 'Warehouse deleted successfully'], 200);
    }
}
