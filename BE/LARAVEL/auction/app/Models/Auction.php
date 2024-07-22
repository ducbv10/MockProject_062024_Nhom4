<?php
namespace App\Models;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class Auction extends Model
{
    use HasFactory, SoftDeletes;

    protected $table = 'Auction';
    protected $primaryKey = 'AuctionId';
    protected $keyType = 'string';
    public $incrementing = false;
    protected $fillable = [
        'AuctionId', 'Name', 'Method', 'IsSecret', 'Status', 'StartDate', 'EndDate', 'DeletedAt'
    ];

    protected $dates = ['DeletedAt'];
    const DELETED_AT = 'DeletedAt';
    public $timestamps = false;

    public function auctionDetails()
    {
        return $this->hasMany(AuctionDetail::class, 'AuctionId', 'AuctionId');
    }
}
