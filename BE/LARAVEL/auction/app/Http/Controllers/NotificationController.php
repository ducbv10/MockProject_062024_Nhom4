<?php

namespace App\Http\Controllers;

use App\Http\Controllers\Controller;
use App\Models\Notification;
use Illuminate\Http\Request;

/**
 * @group Notifications
 *
 * APIs for managing notifications
 */
class NotificationController extends Controller
{
    /**
     * Display a listing of the notifications.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return Notification::all();
    }

    /**
     * Store a newly created notification in storage.
     *
     * @bodyParam UserId string required The ID of the user associated with the notification. Example: U12345
     * @bodyParam Content string required The content of the notification. Example: New message received.
     * @bodyParam Title string required The title of the notification. Example: New Message
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $notification = Notification::create($request->all());
        return response()->json($notification, 201);
    }

    /**
     * Display the specified notification.
     *
     * @urlParam id string required The ID of the notification.
     *
     * @param  string  $NotificationId
     * @return \Illuminate\Http\Response
     */
    public function show($NotificationId)
    {
        $notification = Notification::findOrFail($NotificationId);
        return response()->json($notification);
    }

    /**
     * Update the specified notification in storage.
     *
     * @urlParam id string required The ID of the notification.
     * @bodyParam UserId string required The ID of the user associated with the notification. Example: U12345
     * @bodyParam Content string required The content of the notification. Example: Updated message content.
     * @bodyParam Title string required The title of the notification. Example: Updated Message
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  string  $NotificationId
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $NotificationId)
    {
        $notification = Notification::findOrFail($NotificationId);
        $notification->update($request->all());
        return response()->json($notification, 200);
    }

    /**
     * Remove the specified notification from storage.
     *
     * @urlParam id string required The ID of the notification.
     *
     * @param  string  $NotificationId
     * @return \Illuminate\Http\Response
     */
    public function destroy($NotificationId)
    {
        Notification::findOrFail($NotificationId)->delete();
        return response()->json(['message' => 'Notification deleted successfully'], 200);
    }
}
