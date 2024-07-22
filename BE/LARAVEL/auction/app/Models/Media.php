<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class Media extends Model
{
    use HasFactory, SoftDeletes;

    protected $table = 'Media';
    protected $primaryKey = 'MediaId'; 
    protected $keyType = 'string';
    public $incrementing = false;
    protected $fillable = [
        'MediaId', 'AssetId', 'DeletedAt'
    ];

    protected $dates = ['DeletedAt'];
    const DELETED_AT = 'DeletedAt';
    public $timestamps = false;

    public function asset()
    {
        return $this->belongsTo(Asset::class, 'AssetId', 'AssetId');
    }

    public function mediaItems()
    {
        return $this->hasMany(MediaItem::class, 'MediaId', 'MediaId');
    }
}
