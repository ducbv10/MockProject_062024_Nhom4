<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Notification extends Model
{
    use HasFactory;

    protected $primaryKey = 'NotificationId'; // Set primary key field
    public $incrementing = false; // Disable auto-increment for primary key
    protected $keyType = 'string'; // Set primary key type
    public $timestamps = false; // Bỏ qua cài đặt timestamps

    protected $fillable = [
        'NotificationId',
        'UserId',
        'Content',
        'Title',
    ];

    // Define the relationship with User model
    public function user()
    {
        return $this->belongsTo(User::class, 'UserId', 'UserId');
    }
}
