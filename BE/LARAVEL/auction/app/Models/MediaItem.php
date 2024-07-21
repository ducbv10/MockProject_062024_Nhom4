<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class MediaItem extends Model
{
    use HasFactory, SoftDeletes;

    protected $table = 'MediaItem';
    protected $primaryKey = 'MediaItemId'; 
    protected $keyType = 'string';
    public $incrementing = false;
    protected $fillable = [
        'MediaItemId', 'MediaId', 'Value', 'DeletedAt'
    ];

    protected $dates = ['DeletedAt'];
    const DELETED_AT = 'DeletedAt';
    public $timestamps = false;

    public function media()
    {
        return $this->belongsTo(Media::class, 'MediaId', 'MediaId');
    }
}
