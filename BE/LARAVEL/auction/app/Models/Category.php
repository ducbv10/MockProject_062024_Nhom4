<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;

class Category extends Model
{
    use HasFactory, SoftDeletes;
    protected $table = 'Category';
    //protected $primaryKey = 'CategoryId';
    protected $fillable = ['CategoryId', 'CategoryName', 'Description', 'DeletedAt'];

    protected $dates = ['DeletedAt'];

    const DELETED_AT = 'DeletedAt';
    public $timestamps = false;
}
