<?php

namespace App\Http\Controllers;

use App\Models\Category;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class CategoryController extends Controller
{
    protected $category;

    public function __construct()
    {
        $this->category = new Category();
    }

    /**
     * Display a listing of the categories.
     *
     * @return \Illuminate\Http\JsonResponse
     *
     * @group Categories
     * @response {
     *  "data": [
     *      {
     *          "CategoryId": "1",
     *          "CategoryName": "Category 1",
     *          "Description": "Description of Category 1"
     *      },
     *      {
     *          "CategoryId": "2",
     *          "CategoryName": "Category 2",
     *          "Description": "Description of Category 2"
     *      }
     *  ]
     * }
     */
    public function index()
    {
        $categories = Category::all();
        return response()->json(['data' => $categories]);
    }

    /**
     * Store a newly created category in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\JsonResponse
     *
     * @group Categories
     * @bodyParam CategoryId string required The ID of the category. Example: 1
     * @bodyParam CategoryName string required The name of the category. Example: Category 1
     * @bodyParam Description string The description of the category.
     * @response {
     *  "message": "Category created successfully",
     *  "data": {
     *      "CategoryId": "1",
     *      "CategoryName": "Category 1",
     *      "Description": "Description of Category 1"
     *  }
     * }
     * @response 400 {
     *  "error": {
     *      "CategoryId": [
     *          "The category ID has already been taken."
     *      ]
     *  }
     * }
     */
    public function store(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'CategoryId' => 'required|string|max:10|unique:categories',
            'CategoryName' => 'required|string|max:255',
            'Description' => 'nullable|string',
        ]);

        if ($validator->fails()) {
            return response()->json(['error' => $validator->errors()], 400);
        }

        $category = Category::create([
            'CategoryId' => $request->input('CategoryId'),
            'CategoryName' => $request->input('CategoryName'),
            'Description' => $request->input('Description'),
        ]);

        return response()->json([
            'message' => 'Category created successfully',
            'data' => $category,
        ], 201);
    }

    /**
     * Display the specified category.
     *
     * @param  string  $id
     * @return \Illuminate\Http\JsonResponse
     *
     * @group Categories
     * @urlParam id required The ID of the category. Example: 1
     * @response {
     *  "data": {
     *      "CategoryId": "1",
     *      "CategoryName": "Category 1",
     *      "Description": "Description of Category 1"
     *  }
     * }
     * @response 404 {
     *  "error": "Not Found"
     * }
     */
    public function show($id)
    {
        $category = Category::findOrFail($id);
        return response()->json(['data' => $category]);
    }
/**
 * Update the specified category in storage.
 *
 * @param  \Illuminate\Http\Request  $request
 * @param  string  $id
 * @return \Illuminate\Http\JsonResponse
 *
 * @group Categories
 * @urlParam id required The ID of the category to update. Example: 1
 * @bodyParam CategoryName string required The new name of the category. Example: Updated Category
 * @bodyParam Description string The new description of the category.
 * @response {
 *  "message": "Category updated successfully",
 *  "data": {
 *      "CategoryId": "1",
 *      "CategoryName": "Updated Category",
 *      "Description": "New description"
 *  }
 * }
 * @response 400 {
 *  "error": {
 *      "CategoryName": [
 *          "The category name field is required."
 *      ]
 *  }
 * }
 * @response 404 {
 *  "error": "Category not found."
 * }
 */
public function update(Request $request, $id)
{
    $category = Category::findOrFail($id);

    $validator = Validator::make($request->all(), [
        'CategoryName' => 'required|string|max:255',
        'Description' => 'nullable|string',
    ]);

    if ($validator->fails()) {
        return response()->json(['error' => $validator->errors()], 400);
    }

    $category->update([
        'CategoryName' => $request->input('CategoryName'),
        'Description' => $request->input('Description'),
    ]);

    return response()->json([
        'message' => 'Category updated successfully',
        'data' => $category,
    ], 200);
}

    /**
     * Remove the specified category from storage.
     *
     * @param  string  $id
     * @return \Illuminate\Http\JsonResponse
     *
     * @group Categories
     * @urlParam id required The ID of the category to delete. Example: 1
     * @response {
     *  "message": "Category deleted successfully"
     * }
     * @response 404 {
     *  "error": "Category not found."
     * }
     */
    public function destroy($id)
    {
        $category = Category::find($id);

        if (!$category) {
            return response()->json(['error' => 'Category not found.'], 404);
        }

        $category->delete();

        return response()->json([
            'message' => 'Category deleted successfully',
        ], 200);
    }

    /**
     * Restore the specified category from soft delete.
     *
     * @param  string  $id
     * @return \Illuminate\Http\JsonResponse
     *
     * @group Categories
     * @urlParam id required The ID of the category to restore. Example: 1
     * @response {
     *  "message": "Category restored successfully",
     *  "data": {
     *      "CategoryId": "1",
     *      "CategoryName": "Restored Category",
     *      "Description": "Restored description"
     *  }
     * }
     * @response 404 {
     *  "error": "Category not found."
     * }
     */
    public function restore($id)
    {
        $category = Category::withTrashed()->find($id);

        if (!$category) {
            return response()->json(['error' => 'Category not found.'], 404);
        }

        $category->restore();

        return response()->json([
            'message' => 'Category restored successfully',
            'data' => $category,
        ], 200);
    }
}
