<?php

namespace App\Http\Controllers;

use App\Models\Appraiser;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class AppraiserController extends Controller
{
    protected $appraiser;

    public function __construct()
    {
        $this->appraiser = new Appraiser();
    }

    
     //@return \Illuminate\Http\JsonResponse
     
    public function index()
    {
        $appraisers = Appraiser::all();
        return response()->json(['data' => $appraisers]);
    }

    //@param  \Illuminate\Http\Request  $request
     // @return \Illuminate\Http\JsonResponse
         public function store(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'AppraiserId' => 'required|string|max:10|unique:Appraiser',
            'Name' => 'required|string',
            'Experience' => 'nullable|string',
            'Email' => 'nullable|string',
            'Phone' => 'nullable|string',
            'Address' => 'nullable|string',
            
        ]);

        if ($validator->fails()) {
            return response()->json(['error' => $validator->errors()], 400);
        }

        try {
            $data = $request->only([
               'AppraiserId', 'Name', 'Experience', 'Email', 'Phone',
        'Address',
            ]);

            $appraiser = Appraiser::create($data);

            return response()->json([
                'message' => 'Appraiser created successfully',
                'data' => $appraiser,
            ], 201);
        } catch (\Exception $e) {
            return response()->json(['error' => 'Failed to create appraiser.'], 500);
        }
    }

       public function show($id)
    {
        $appraiser = Appraiser::findOrFail($id);
        return response()->json(['data' => $appraiser]);
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
     */
    public function update(Request $request, $id)
    {
        $appraiser = Appraiser::findOrFail($id);

        $validator = Validator::make($request->all(), [
            'Name' => 'required|string',
            'Experience' => 'nullable|string',
            'Email' => 'nullable|string',
            'Phone' => 'nullable|string',
            'Address' => 'nullable|string',
        ]);

        if ($validator->fails()) {
            return response()->json(['error' => $validator->errors()], 400);
        }

        $appraiser->update($request->all());

        return response()->json([
            'message' => 'Appraiser updated successfully',
            'data' => $appraiser,
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
     * @group Appraiser
     * @urlParam id required The ID of the asset. Example:AP00000001
     *
     * @response 200 {
     *     "message": "Appraiser deleted successfully"
     * }
     *
     * @response 404 {
     *     "error": "Asset not found."
     * }
     */
    public function destroy($id)
    {
        $appraiser = Appraiser::find($id);

        if (!$appraiser) {
            return response()->json(['error' => 'Appraiser not found.'], 404);
        }

        $appraiser->delete();

        return response()->json([
            'message' => 'Appraiser deleted successfully',
        ], 200);
    }

  
    public function restore($id)
    {
        $appraiser = Appraiser::withTrashed()->find($id);

        if (!$appraiser) {
            return response()->json(['error' => 'Appraiser not found.'], 404);
        }

        $appraiser->restore();

        return response()->json([
            'message' => 'Asset restored successfully',
            'data' => $appraiser,
        ], 200);
    }
}
