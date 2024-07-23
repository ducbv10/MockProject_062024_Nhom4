<?php

use App\Http\Controllers\AppraiserController;
use App\Http\Controllers\AssetController;
use App\Http\Controllers\CategoryController;
use App\Http\Controllers\NotificationController;
use App\Http\Controllers\UserController;
use App\Http\Controllers\WarehouseController;
use App\Http\Controllers\AuctionController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

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

Route::get('/auctioned-products', [AuctionController::class, 'auctionedProducts']);
Route::get('/successfully-auctioned-products', [AuctionController::class, 'successfullyAuctionedProducts']);

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
    Route::get('/auctioned-products', [AssetController::class, 'getAuctionedProducts']);
    Route::get('/successfully-auctioned-products', [AssetController::class, 'getSuccessfullyAuctionedProducts']);
});
Route::prefix('appraisers')->group(function () {
    Route::get('/', [AppraiserController::class, 'index']); // Get All Assets
    Route::get('/{id}', [AppraiserController::class, 'show']); // Get Asset by ID
    Route::post('/', [AppraiserController::class, 'store']); // Create a New Asset
    Route::put('/{id}', [AppraiserController::class, 'update']); // Update Asset
    Route::delete('/{id}', [AppraiserController::class, 'destroy']); // Soft Delete Asset
    Route::patch('/restore/{id}', [AppraiserController::class, 'restore']); // Restore Soft Deleted Asset
});

Route::get('/notifications', [NotificationController::class, 'index']);
Route::get('/notifications/{NotificationId}', [NotificationController::class, 'show']);
Route::post('/notifications', [NotificationController::class, 'store']);
Route::put('/notifications/{NotificationId}', [NotificationController::class, 'update']);
Route::delete('/notifications/{NotificationId}', [NotificationController::class, 'destroy']);

Route::get('warehouses', [WarehouseController::class, 'index']);
Route::get('warehouses/{WareHouseId}', [WarehouseController::class, 'show']);
Route::post('warehouses', [WarehouseController::class, 'store']);
Route::put('warehouses/{WareHouseId}', [WarehouseController::class, 'update']);
Route::delete('warehouses/{WareHouseId}', [WarehouseController::class, 'destroy']);

Route::put('users/{UserId}', [UserController::class, 'update']);
