<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Asset extends Model
{
    use HasFactory;
    protected $fillable = [
        'name', 'description', 'origin_location', 'organization', 
        'appraiser_status', 'status', 'is_auctioned', 'is_deleted', 
        'appraised_price', 'auction_price', 'category_id', 
        'warehouse_id', 'appraiser_id'
    ];
    
    public function category()
    {
        return $this->belongsTo(Category::class);
    }
}
