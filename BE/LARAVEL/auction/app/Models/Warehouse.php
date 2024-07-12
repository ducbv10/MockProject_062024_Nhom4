<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Warehouse extends Model
{
    use HasFactory;

    protected $primaryKey = 'WareHouseId'; // Set primary key field
    public $incrementing = false; // Disable auto-increment for primary key
    protected $keyType = 'string'; // Set primary key type
    public $timestamps = false; // Bỏ qua cài đặt timestamps

    protected $fillable = [
        'WareHouseId',
        'Position',
        'Quantity',
        'importDate',
        'exportDate',
    ];
}
