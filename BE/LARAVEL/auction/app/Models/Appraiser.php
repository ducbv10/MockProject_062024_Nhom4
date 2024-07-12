<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\SoftDeletes;
class Appraiser extends Model
{
    use HasFactory;
    use SoftDeletes;
    protected $table = 'Appraiser';
    protected $primaryKey = 'AppraiserId'; 

    protected $fillable = [
        'AppraiserId', 'Name', 'Experience', 'Email', 'Phone',
        'Address', 'DeletedAt'
    ];

    protected $dates = ['DeletedAt'];
    const DELETED_AT = 'DeletedAt';
    public $timestamps = false;


}