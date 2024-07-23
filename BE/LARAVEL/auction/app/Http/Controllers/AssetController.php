<?php

namespace App\Http\Controllers;

use App\Models\Asset;
use App\Models\Media;
use App\Models\MediaItem;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Facades\DB;

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
        $assets = Asset::with('media.mediaItems')->get();

    // // Chuyển đổi dữ liệu để bao gồm hình ảnh
    // $assets = $assets->map(function ($asset) {
    //     $mediaItems = $asset->media->flatMap->mediaItems;
    //     $images = $mediaItems->pluck('Value');
    //     $asset->images = $images;
    //     unset($asset->media); // Xóa trường media nếu không cần thiết
    //     return $asset;
    // });

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
            'media' => 'nullable|array',
            'media.*.MediaId' => 'required|string|max:10',
            'media.*.MediaItems' => 'nullable|array',
            'media.*.MediaItems.*.MediaItemId' => 'required|string|max:10',
            'media.*.MediaItems.*.Value' => 'required|string',
        ]);

         if ($validator->fails()) {
            return response()->json(['error' => $validator->errors()], 400);
        }

        DB::beginTransaction();
        try {
            $data = $request->only([
                'AssetId', 'Name', 'Description', 'Origin', 'AppraiserStatus',
                'IsNew', 'IsInAuction', 'IsSold', 'UserId', 'AppraiserId',
                'WareHouseId', 'CategoryId'
            ]);

            $asset = Asset::create($data);

            if ($request->has('media')) {
                foreach ($request->media as $mediaData) {
                    $media = new Media([
                        'MediaId' => $mediaData['MediaId'],
                        'AssetId' => $asset->AssetId,
                    ]);
                    $asset->media()->save($media);

                    if (isset($mediaData['MediaItems'])) {
                        foreach ($mediaData['MediaItems'] as $mediaItemData) {
                            $mediaItem = new MediaItem([
                                'MediaItemId' => $mediaItemData['MediaItemId'],
                                'MediaId' => $media->MediaId,
                                'Value' => $mediaItemData['Value'],
                            ]);
                            $media->mediaItems()->save($mediaItem);
                        }
                    }
                }
            }

            DB::commit();
            return response()->json([
                'message' => 'Asset created successfully',
                'data' => $asset->load('media.mediaItems'),
            ], 201);
        } catch (\Exception $e) {
            DB::rollBack();
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
        $asset = Asset::with(['media.mediaItems'])->findOrFail($id);
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
            'media' => 'nullable|array',
            'media.*.MediaId' => 'required|string|max:10',
            'media.*.MediaItems' => 'nullable|array',
            'media.*.MediaItems.*.MediaItemId' => 'required|string|max:10',
            'media.*.MediaItems.*.Value' => 'required|string',
        ]);

         if ($validator->fails()) {
            return response()->json(['error' => $validator->errors()], 400);
        }

        DB::beginTransaction();
        try {
            $asset->update($request->only([
                'Name', 'Description', 'Origin', 'AppraiserStatus',
                'IsNew', 'IsInAuction', 'IsSold', 'UserId', 'AppraiserId',
                'WareHouseId', 'CategoryId'
            ]));

            if ($request->has('media')) {
                $asset->media()->delete();
                foreach ($request->media as $mediaData) {
                    $media = new Media([
                        'MediaId' => $mediaData['MediaId'],
                        'AssetId' => $asset->AssetId,
                    ]);
                    $asset->media()->save($media);

                    if (isset($mediaData['MediaItems'])) {
                        foreach ($mediaData['MediaItems'] as $mediaItemData) {
                            $mediaItem = new MediaItem([
                                'MediaItemId' => $mediaItemData['MediaItemId'],
                                'MediaId' => $media->MediaId,
                                'Value' => $mediaItemData['Value'],
                            ]);
                            $media->mediaItems()->save($mediaItem);
                        }
                    }
                }
            }

            DB::commit();
            return response()->json([
                'message' => 'Asset updated successfully',
                'data' => $asset->load('media.mediaItems'),
            ], 200);
        } catch (\Exception $e) {
            DB::rollBack();
            return response()->json(['error' => 'Failed to update asset.'], 500);
        }
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

        DB::beginTransaction();
        try {
            $asset->media()->delete();
            $asset->delete();

            DB::commit();
            return response()->json(['message' => 'Asset deleted successfully.'], 200);
        } catch (\Exception $e) {
            DB::rollBack();
            return response()->json(['error' => 'Failed to delete asset.'], 500);
        }
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
         $asset = Asset::onlyTrashed()->find($id);

        if (!$asset) {
            return response()->json(['error' => 'Asset not found or not deleted.'], 404);
        }

        DB::beginTransaction();
        try {
            $asset->restore();

            // Restore related media and media items if necessary
            foreach ($asset->media()->withTrashed()->get() as $media) {
                $media->restore();
                foreach ($media->mediaItems()->withTrashed()->get() as $mediaItem) {
                    $mediaItem->restore();
                }
            }
            DB::commit();
            return response()->json(['message' => 'Asset restored successfully.', 'data' => $asset->load('media.mediaItems')], 200);
        } catch (\Exception $e) {
            DB::rollBack();
            return response()->json(['error' => 'Failed to restore asset.'], 500);
        }
    }
    // Trả về tất cả sản phẩm đang đấu giá
    public function getAuctionedProducts()
    {
        $auctionedProducts = DB::table('Auction as a')
            ->join('AuctionDetail as ad', 'a.AuctionId', '=', 'ad.AuctionId')
            ->join('Asset as ass', 'ad.AssetId', '=', 'ass.AssetId')
            ->leftJoin('Media as m', 'm.AssetId', '=', 'ass.AssetId')
            ->leftJoin('MediaItem as mi', 'mi.MediaId', '=', 'm.MediaId')
            ->select('a.StartDate', 'mi.Value as Image', 'ass.Name', 'ad.StartingPrice')
            ->where('ass.IsInAuction', 'inauctioned')
            ->whereNull('mi.DeletedAt')
            ->get();

        return response()->json($auctionedProducts);
    }

    // Trả về tất cả sản phẩm đã đấu giá thành công
    public function getSuccessfullyAuctionedProducts()
    {
        $successfullyAuctionedProducts = DB::table('Auction as a')
            ->join('AuctionDetail as ad', 'a.AuctionId', '=', 'ad.AuctionId')
            ->join('Asset as ass', 'ad.AssetId', '=', 'ass.AssetId')
            ->leftJoin('Media as m', 'm.AssetId', '=', 'ass.AssetId')
            ->leftJoin('MediaItem as mi', 'mi.MediaId', '=', 'm.MediaId')
            ->select('mi.Value as Image', 'ass.Name', 'a.EndDate', 'ad.PresentPrice as FinalPrice')
            ->where('a.Status', 'finished')
            ->whereNull('mi.DeletedAt')
            ->get();

        return response()->json($successfullyAuctionedProducts);
    }
}
