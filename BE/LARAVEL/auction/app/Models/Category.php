<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class Category extends Model
{
    use HasFactory, SoftDeletes;
    protected $table = 'Category';
    protected $primaryKey = 'CategoryId';
    protected $keyType = 'string';
    public $incrementing = false;
    protected $fillable = ['CategoryId', 'CategoryName', 'Description', 'DeletedAt'];
    protected $dates = ['DeletedAt'];
    const DELETED_AT = 'DeletedAt';
    public $timestamps = false;
    
    public function assets()
        {
            return $this->hasMany(Asset::class, 'CategoryId', 'CategoryId');
        }
}
