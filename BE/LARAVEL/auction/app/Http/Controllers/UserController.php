<?php
namespace App\Http\Controllers;

use App\Models\User;
use Illuminate\Http\Request;

class UserController extends Controller
{
    /**
     * Update a user record.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  string  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        $request->validate([
            'UserName' => 'string|max:255',
            'PassWord' => 'string|max:255',
            'Avatar' => 'string|nullable|max:255',
            'Name' => 'string|max:255',
            'DateOfBirth' => 'date|nullable',
            'Gender' => 'in:male,female|nullable',
            'Phone' => 'string|nullable|max:255',
            'Email' => 'string|max:255|email',
            'AddressId' => 'string|nullable|max:10',
            'IsBan' => 'in:banned,unbanned',
            'Verify' => 'in:verified,unverified',
        ]);

        // Find the user by ID
        $user = User::findOrFail($id);

        // Update the user with validated data
        $user->update($request->all());

        // Return the updated user as JSON response
        return response()->json($user, 200);
    }
}
