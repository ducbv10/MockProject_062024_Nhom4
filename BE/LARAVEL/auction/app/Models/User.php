<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class User extends Model
{
    use HasFactory;

    protected $primaryKey = 'UserId'; // Define primary key field name
    public $incrementing = false; // Ensure primary key is not auto-incrementing

    protected $fillable = [
        'UserId',
        'UserName',
        'PassWord',
        'Avatar',
        'Name',
        'DateOfBirth',
        'Gender',
        'Phone',
        'Email',
        'AddressId',
        'IsBan',
        'Verify',
    ];

    protected $casts = [
        'DateOfBirth' => 'date', // Cast DateOfBirth to date format
    ];

}
