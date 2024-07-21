<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class AuctionDetail extends Model
{
    use HasFactory, SoftDeletes;

    protected $table = 'AuctionDetail';
    protected $primaryKey = 'AuctionDetailId';
    protected $keyType = 'string';
    public $incrementing = false;
    protected $fillable = [
        'AuctionDetailId', 'StartingPrice', 'PresentPrice', 'PercentPrice', 'Step',
        'WonId', 'TotalTime', 'AuctionId', 'HostId', 'AssetId', 'DeletedAt'
    ];

    protected $dates = ['DeletedAt'];
    const DELETED_AT = 'DeletedAt';
    public $timestamps = false;

    public function auction()
    {
        return $this->belongsTo(Auction::class, 'AuctionId');
    }

    public function asset()
    {
        return $this->belongsTo(Asset::class, 'AssetId');
    }
}
