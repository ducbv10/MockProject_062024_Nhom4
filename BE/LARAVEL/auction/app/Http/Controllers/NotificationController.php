<?php

namespace App\Http\Controllers;

use App\Models\Notification;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class NotificationController extends Controller
{
    /**
     * Display a listing of the notifications.
     *
     * @return \Illuminate\Http\JsonResponse
     *
     * @group Notifications
     * @response {
     *  "data": [
     *      {
     *          "NotificationId": "1",
     *          "Content": "Lorem ipsum...",
     *          "Title": "Notification 1"
     *      },
     *      {
     *          "NotificationId": "2",
     *          "Content": "Dolor sit amet...",
     *          "Title": "Notification 2"
     *      }
     *  ]
     * }
     */
    public function index()
    {
        $notifications = Notification::all();

        return response()->json([
            'data' => $notifications,
        ]);
    }

    /**
     * Display the specified notification.
     *
     * @param  string  $NotificationId
     * @return \Illuminate\Http\JsonResponse
     *
     * @group Notifications
     * @urlParam NotificationId required The ID of the notification. Example: 1
     * @response {
     *  "data": {
     *      "NotificationId": "1",
     *      "Content": "Lorem ipsum...",
     *      "Title": "Notification 1"
     *  }
     * }
     */
    public function show($NotificationId)
    {
        $notification = Notification::find($NotificationId);

        if (!$notification) {
            return response()->json(['error' => 'Notification not found.'], 404);
        }

        return response()->json([
            'data' => $notification,
        ]);
    }

    /**
     * Store a newly created notification in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\JsonResponse
     *
     * @group Notifications
     * @bodyParam NotificationId string required The ID of the notification. Example: 1
     * @bodyParam Content string required The content of the notification.
     * @bodyParam Title string required The title of the notification.
     * @response {
     *  "message": "Notification created successfully",
     *  "data": {
     *      "NotificationId": "1",
     *      "Content": "Lorem ipsum...",
     *      "Title": "Notification 1"
     *  }
     * }
     */
    public function store(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'NotificationId' => 'required|string|max:10|unique:notifications',
            'Content' => 'required|string',
            'Title' => 'required|string',
        ]);

        if ($validator->fails()) {
            return response()->json(['error' => $validator->errors()], 400);
        }

        $notification = Notification::create([
            'NotificationId' => $request->input('NotificationId'),
            'Content' => $request->input('Content'),
            'Title' => $request->input('Title'),
        ]);

        return response()->json([
            'message' => 'Notification created successfully',
            'data' => $notification,
        ], 201);
    }

    /**
     * Update the specified notification in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  string  $NotificationId
     * @return \Illuminate\Http\JsonResponse
     *
     * @group Notifications
     * @urlParam NotificationId required The ID of the notification to update. Example: 1
     * @bodyParam Content string required The new content of the notification.
     * @bodyParam Title string required The new title of the notification.
     * @response {
     *  "message": "Notification updated successfully",
     *  "data": {
     *      "NotificationId": "1",
     *      "Content": "Updated content...",
     *      "Title": "Updated Notification"
     *  }
     * }
     */
    public function update(Request $request, $NotificationId)
    {
        $notification = Notification::find($NotificationId);

        if (!$notification) {
            return response()->json(['error' => 'Notification not found.'], 404);
        }

        $validator = Validator::make($request->all(), [
            'Content' => 'required|string',
            'Title' => 'required|string',
        ]);

        if ($validator->fails()) {
            return response()->json(['error' => $validator->errors()], 400);
        }

        $notification->update([
            'Content' => $request->input('Content'),
            'Title' => $request->input('Title'),
        ]);

        return response()->json([
            'message' => 'Notification updated successfully',
            'data' => $notification,
        ]);
    }

    /**
     * Remove the specified notification from storage.
     *
     * @param  string  $NotificationId
     * @return \Illuminate\Http\JsonResponse
     *
     * @group Notifications
     * @urlParam NotificationId required The ID of the notification to delete. Example: 1
     * @response {
     *  "message": "Notification deleted successfully",
     *  "data": {
     *      "NotificationId": "1",
     *      "Content": "Deleted content...",
     *      "Title": "Deleted Notification"
     *  }
     * }
     */
    public function destroy($NotificationId)
    {
        $notification = Notification::find($NotificationId);

        if (!$notification) {
            return response()->json(['error' => 'Notification not found.'], 404);
        }

        $notification->delete();

        return response()->json([
            'message' => 'Notification deleted successfully',
            'data' => $notification,
        ]);
    }
}
