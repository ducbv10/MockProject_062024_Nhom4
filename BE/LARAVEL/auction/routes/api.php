<?php

use App\Http\Controllers\NotificationController;
use Illuminate\Http\Request;use Illuminate\Support\Facades\Route;
use App\Http\Controllers\CategoryController;
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

Route::prefix('category')->group(function () {
    Route::get('/', [CategoryController::class, 'index']);
    Route::get('/{id}', [CategoryController::class, 'show']);
    Route::post('/', [CategoryController::class, 'store']);
    Route::put('/{id}', [CategoryController::class, 'update']);
    Route::delete('/{id}', [CategoryController::class, 'destroy']);
    Route::patch('/restore/{id}', [CategoryController::class, 'restore']);
});

Route::get('/notifications', [NotificationController::class, 'index']);
Route::get('/notifications/{NotificationId}', [NotificationController::class, 'show']);
Route::post('/notifications', [NotificationController::class, 'store']);
Route::put('/notifications/{NotificationId}', [NotificationController::class, 'update']);
Route::delete('/notifications/{NotificationId}', [NotificationController::class, 'destroy']);
