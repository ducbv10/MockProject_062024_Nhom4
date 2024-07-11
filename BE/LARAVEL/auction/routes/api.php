<?php

use App\Http\Controllers\NotificationController;
use Illuminate\Http\Request;use Illuminate\Support\Facades\Route;
use App\Http\Controllers\CategoryController;
use App\Http\Controllers\AssetController;
/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
 */

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});

Route::prefix('categories')->group(function () {
    Route::get('/', [CategoryController::class, 'index']); // Get All Categories
    Route::get('/{id}', [CategoryController::class, 'show']); // Get Category by ID
    Route::post('/', [CategoryController::class, 'store']); // Create a New Category
    Route::put('/{id}', [CategoryController::class, 'update']); // Update Category
    Route::delete('/{id}', [CategoryController::class, 'destroy']); // Soft Delete Category
    Route::patch('/restore/{id}', [CategoryController::class, 'restore']); // Restore Soft Deleted Category
});

Route::prefix('assets')->group(function () {
    Route::get('/', [AssetController::class, 'index']); // Get All Assets
    Route::get('/{id}', [AssetController::class, 'show']); // Get Asset by ID
    Route::post('/', [AssetController::class, 'store']); // Create a New Asset
    Route::put('/{id}', [AssetController::class, 'update']); // Update Asset
    Route::delete('/{id}', [AssetController::class, 'destroy']); // Soft Delete Asset
    Route::patch('/restore/{id}', [AssetController::class, 'restore']); // Restore Soft Deleted Asset
});

Route::get('/notifications', [NotificationController::class, 'index']);
Route::get('/notifications/{NotificationId}', [NotificationController::class, 'show']);
Route::post('/notifications', [NotificationController::class, 'store']);
Route::put('/notifications/{NotificationId}', [NotificationController::class, 'update']);
Route::delete('/notifications/{NotificationId}', [NotificationController::class, 'destroy']);
