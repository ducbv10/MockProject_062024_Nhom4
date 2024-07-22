<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class Asset extends Model
{
    use HasFactory;
    use SoftDeletes;
    protected $table = 'Asset';
    protected $primaryKey = 'AssetId'; 
    protected $keyType = 'string';
    public $incrementing = false;
    protected $fillable = [
        'AssetId', 'Name', 'Description', 'Origin', 'AppraiserStatus',
        'IsNew', 'IsInAuction', 'IsSold', 'UserId', 'AppraiserId',
        'WareHouseId', 'CategoryId', 'DeletedAt'
    ];

    protected $dates = ['DeletedAt'];
    const DELETED_AT = 'DeletedAt';
    public $timestamps = false;

    public function category()
    {
        return $this->belongsTo(Category::class, 'CategoryId', 'CategoryId');
    }

    public function media()
    {
        return $this->hasMany(Media::class, 'AssetId', 'AssetId');
    }

    public function auctionDetail()
    {
        return $this->hasOne(AuctionDetail::class, 'AssetId', 'AssetId');
    }
}
