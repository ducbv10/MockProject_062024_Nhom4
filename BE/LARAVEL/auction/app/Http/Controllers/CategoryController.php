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

    public function index()
    {
        $category = Category::all();
        return response()->json($category);
    }

    public function store(Request $request)
    {
        $validate = [
            'CategoryId' => 'required|string|max:10|unique:Category,CategoryId',
            'CategoryName' => 'required|string|max:255',
            'Description' => 'nullable|string',
        ];
        $validator = Validator::make($request->all(), $validate);

        if ($validator->fails()) {
            return response()->json(['error' => $validator->errors()], 400);
        }

        $dataInsert = $validator->validated();
        $category = Category::create($dataInsert);

        return response()->json([
            'message' => 'Category created successfully',
            'data' => $category,
        ], 201);
    }

    public function show($id)
    {
        $category = Category::findOrFail($id);
        return response()->json($category);
    }

    public function update(Request $request, $id)
    {
        $category = Category::findOrFail($id);

        $validate = [
            'CategoryId' => 'required|string|max:10|unique:Category,CategoryId,' . $id . ',CategoryId',
            'CategoryName' => 'required|string|max:255',
            'Description' => 'nullable|string',
        ];
        $validator = Validator::make($request->all(), $validate);

        if ($validator->fails()) {
            return response()->json(['errors' => $validator->errors()], 400);
        }

        $dataUpdate = $validator->validated();
        $category->update($dataUpdate);

        return response()->json([
            'message' => 'Category updated successfully',
            'data' => $category,
        ], 200);
    }

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