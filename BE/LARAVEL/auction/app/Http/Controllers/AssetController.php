<?php

namespace App\Http\Controllers;

use App\Models\Asset;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class AssetController extends Controller
{
    protected $asset;

    public function __construct()
    {
        $this->asset = new Asset();
    }

    /**
     * Get a list of all assets.
     *
     * This endpoint retrieves all assets from the database.
     *
     * @return \Illuminate\Http\JsonResponse
     *
     * @group Assets
     * @response 200 {
     *     "data": [
     *         {
     *             "AssetId": "AS00000001",
     *             "Name": "Asset 1",
     *             "Description": "Description of asset 1",
     *             "Origin": "Origin 1",
     *             "AppraiserStatus": "Appraiser status 1",
     *             "IsNew": "Yes",
     *             "IsInAuction": "No",
     *             "IsSold": "No",
     *             "UserId": "User1",
     *             "AppraiserId": "Appraiser1",
     *             "WareHouseId": "Warehouse1",
     *             "CategoryId": "Category1"
     *         }
     *     ]
     * }
     */
    public function index()
    {
        $assets = Asset::all();
        return response()->json(['data' => $assets]);
    }

    /**
     * Store a newly created asset in storage.
     *
     * This endpoint creates a new asset with the provided details.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\JsonResponse
     *
     * @group Assets
     * @bodyParam AssetId string required The ID of the asset. Example: AS00000001
     * @bodyParam Name string required The name of the asset. Example: Asset 1
     * @bodyParam Description string The description of the asset. Example: This is a description of asset 1.
     * @bodyParam Origin string The origin of the asset. Example: Origin 1
     * @bodyParam AppraiserStatus string The appraiser status of the asset. Example: Approved
     * @bodyParam IsNew string Indicates if the asset is new. Example: Yes
     * @bodyParam IsInAuction string Indicates if the asset is in auction. Example: No
     * @bodyParam IsSold string Indicates if the asset is sold. Example: No
     * @bodyParam UserId string The user ID associated with the asset. Example: User1
     * @bodyParam AppraiserId string The appraiser ID associated with the asset. Example: Appraiser1
     * @bodyParam WareHouseId string The warehouse ID where the asset is stored. Example: Warehouse1
     * @bodyParam CategoryId string The category ID associated with the asset. Example: Category1
     *
     * @response 201 {
     *     "message": "Asset created successfully",
     *     "data": {
     *         "AssetId": "AS00000001",
     *         "Name": "Asset 1",
     *         "Description": "This is a description of asset 1.",
     *         "Origin": "Origin 1",
     *         "AppraiserStatus": "Approved",
     *         "IsNew": "Yes",
     *         "IsInAuction": "No",
     *         "IsSold": "No",
     *         "UserId": "User1",
     *         "AppraiserId": "Appraiser1",
     *         "WareHouseId": "Warehouse1",
     *         "CategoryId": "Category1"
     *     }
     * }
     *
     * @response 400 {
     *     "error": {
     *         "AssetId": [
     *             "The asset ID has already been taken."
     *         ]
     *     }
     * }
     *
     * @response 500 {
     *     "error": "Failed to create asset."
     * }
     */
    public function store(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'AssetId' => 'required|string|max:10|unique:Asset',
            'Name' => 'required|string',
            'Description' => 'nullable|string',
            'Origin' => 'nullable|string',
            'AppraiserStatus' => 'nullable|string',
            'IsNew' => 'nullable|string',
            'IsInAuction' => 'nullable|string',
            'IsSold' => 'nullable|string',
            'UserId' => 'nullable|string|max:10',
            'AppraiserId' => 'nullable|string|max:10',
            'WareHouseId' => 'nullable|string|max:10',
            'CategoryId' => 'nullable|string|max:10',
        ]);

        if ($validator->fails()) {
            return response()->json(['error' => $validator->errors()], 400);
        }

        try {
            $data = $request->only([
                'AssetId', 'Name', 'Description', 'Origin', 'AppraiserStatus',
                'IsNew', 'IsInAuction', 'IsSold', 'UserId', 'AppraiserId',
                'WareHouseId', 'CategoryId'
            ]);

            $asset = Asset::create($data);

            return response()->json([
                'message' => 'Asset created successfully',
                'data' => $asset,
            ], 201);
        } catch (\Exception $e) {
            return response()->json(['error' => 'Failed to create asset.'], 500);
        }
    }

    /**
     * Display the specified asset.
     *
     * This endpoint retrieves the details of a specific asset by its ID.
     *
     * @param  int  $id
     * @return \Illuminate\Http\JsonResponse
     *
     * @group Assets
     * @urlParam id required The ID of the asset. Example: AS00000001
     *
     * @response 200 {
     *     "data": {
     *         "AssetId": "AS00000001",
     *         "Name": "Asset 1",
     *         "Description": "This is a description of asset 1.",
     *         "Origin": "Origin 1",
     *         "AppraiserStatus": "Approved",
     *         "IsNew": "Yes",
     *         "IsInAuction": "No",
     *         "IsSold": "No",
     *         "UserId": "User1",
     *         "AppraiserId": "Appraiser1",
     *         "WareHouseId": "Warehouse1",
     *         "CategoryId": "Category1"
     *     }
     * }
     *
     * @response 404 {
     *     "error": "Asset not found."
     * }
     */
    public function show($id)
    {
        $asset = Asset::findOrFail($id);
        return response()->json(['data' => $asset]);
    }

    /**
     * Update the specified asset in storage.
     *
     * This endpoint updates the details of a specific asset.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\JsonResponse
     *
     * @group Assets
     * @urlParam id required The ID of the asset. Example: AS00000001
     * @bodyParam Name string required The name of the asset. Example: Asset 1
     * @bodyParam Description string The description of the asset. Example: This is a description of asset 1.
     * @bodyParam Origin string The origin of the asset. Example: Origin 1
     * @bodyParam AppraiserStatus string The appraiser status of the asset. Example: Approved
     * @bodyParam IsNew string Indicates if the asset is new. Example: Yes
     * @bodyParam IsInAuction string Indicates if the asset is in auction. Example: No
     * @bodyParam IsSold string Indicates if the asset is sold. Example: No
     * @bodyParam UserId string The user ID associated with the asset. Example: User1
     * @bodyParam AppraiserId string The appraiser ID associated with the asset. Example: Appraiser1
     * @bodyParam WareHouseId string The warehouse ID where the asset is stored. Example: Warehouse1
     * @bodyParam CategoryId string The category ID associated with the asset. Example: Category1
     *
     * @response 200 {
     *     "message": "Asset updated successfully",
     *     "data": {
     *         "AssetId": "AS00000001",
     *         "Name": "Asset 1",
     *         "Description": "This is a description of asset 1.",
     *         "Origin": "Origin 1",
     *         "AppraiserStatus": "Approved",
     *         "IsNew": "Yes",
     *         "IsInAuction": "No",
     *         "IsSold": "No",
     *         "UserId": "User1",
     *         "AppraiserId": "Appraiser1",
     *         "WareHouseId": "Warehouse1",
     *         "CategoryId": "Category1"
     *     }
     * }
     *
     * @response 400 {
     *     "error": {
     *         "Name": [
     *             "The name field is required."
     *         ]
     *     }
     * }
     *
     * @response 404 {
     *     "error": "Asset not found."
     * }
     */
    public function update(Request $request, $id)
    {
        $asset = Asset::findOrFail($id);

        $validator = Validator::make($request->all(), [
            'Name' => 'required|string',
            'Description' => 'nullable|string',
            'Origin' => 'nullable|string',
            'AppraiserStatus' => 'nullable|string',
            'IsNew' => 'nullable|string',
            'IsInAuction' => 'nullable|string',
            'IsSold' => 'nullable|string',
            'UserId' => 'nullable|string|max:10',
            'AppraiserId' => 'nullable|string|max:10',
            'WareHouseId' => 'nullable|string|max:10',
            'CategoryId' => 'nullable|string|max:10',
        ]);

        if ($validator->fails()) {
            return response()->json(['error' => $validator->errors()], 400);
        }

        $asset->update($request->all());

        return response()->json([
            'message' => 'Asset updated successfully',
            'data' => $asset,
        ], 200);
    }

    /**
     * Remove the specified asset from storage.
     *
     * This endpoint deletes a specific asset by its ID.
     *
     * @param  int  $id
     * @return \Illuminate\Http\JsonResponse
     *
     * @group Assets
     * @urlParam id required The ID of the asset. Example: AS00000001
     *
     * @response 200 {
     *     "message": "Asset deleted successfully"
     * }
     *
     * @response 404 {
     *     "error": "Asset not found."
     * }
     */
    public function destroy($id)
    {
        $asset = Asset::find($id);

        if (!$asset) {
            return response()->json(['error' => 'Asset not found.'], 404);
        }

        $asset->delete();

        return response()->json([
            'message' => 'Asset deleted successfully',
        ], 200);
    }

    /**
     * Restore the specified asset from storage.
     *
     * This endpoint restores a deleted asset by its ID.
     *
     * @param  int  $id
     * @return \Illuminate\Http\JsonResponse
     *
     * @group Assets
     * @urlParam id required The ID of the asset. Example: AS00000001
     *
     * @response 200 {
     *     "message": "Asset restored successfully",
     *     "data": {
     *         "AssetId": "AS00000001",
     *         "Name": "Asset 1",
     *         "Description": "This is a description of asset 1.",
     *         "Origin": "Origin 1",
     *         "AppraiserStatus": "Approved",
     *         "IsNew": "Yes",
     *         "IsInAuction": "No",
     *         "IsSold": "No",
     *         "UserId": "User1",
     *         "AppraiserId": "Appraiser1",
     *         "WareHouseId": "Warehouse1",
     *         "CategoryId": "Category1"
     *     }
     * }
     *
     * @response 404 {
     *     "error": "Asset not found."
     * }
     */
    public function restore($id)
    {
        $asset = Asset::withTrashed()->find($id);

        if (!$asset) {
            return response()->json(['error' => 'Asset not found.'], 404);
        }

        $asset->restore();

        return response()->json([
            'message' => 'Asset restored successfully',
            'data' => $asset,
        ], 200);
    }
}
