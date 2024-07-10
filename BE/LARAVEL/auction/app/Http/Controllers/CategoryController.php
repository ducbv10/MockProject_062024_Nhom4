<?php

namespace App\Http\Controllers;
use App\Models\Category;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;
class CategoryController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    protected $categories;
    public function __construct()
    {
        $this-> categories = new Category();
    }
    public function index()
    {
        $categories = Category::all();
        return response()->json($categories); 
    }


    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $validate = [
            'name' => 'required|string|max:255|unique:categories',
        ];
        $validator = validator::make($request ->all(),$validate);
        if ($validator->fails()) {
            return response()->json(['error' => $validator->errors()], 400);
        }
        $dataInsert = $validator -> validated();
        $category = Category::create($dataInsert);
        return response()->json( ['message' => 'Category created successfully',
            'data' => $category,
        ], 200);
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        $categories = Category::findOrFail($id);
        return response()->json($categories);
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        $category = Category::findOrFail($id);

         $validate = [
        'name' => 'required|string|max:255|unique:categories,name,' . $id,
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

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        //
    }
}
